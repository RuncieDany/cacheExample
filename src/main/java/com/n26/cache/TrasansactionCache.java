package com.n26.cache;

import com.google.common.primitives.Longs;
import com.n26.Cache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TrasansactionCache implements Cache {

	private final ConcurrentHashMap<String, SoftReference<Object>> cache = new ConcurrentHashMap<>();
	private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();

	public TrasansactionCache() {
		Thread cleanerThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();
					cache.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

	@Override
	public void add(String key, Object value, long periodInMillis) {
		if (key == null) {
			return;
		}
		if (value == null) {
			cache.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + periodInMillis;
			SoftReference<Object> reference = new SoftReference<>(value);
			cache.put(key, reference);
			cleaningUpQueue.put(new DelayedCacheObject(key, reference, expiryTime));
		}
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}

	@Override
	public Object get(String key) {
		return Optional.ofNullable(cache.get(key)).map(SoftReference::get).orElse(null);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public long size() {
		return cache.size();
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	private static class DelayedCacheObject implements Delayed {
		
		
		
		private final String key;
		
		private final SoftReference<Object> reference;
		private final long expiryTime;
		
		public  DelayedCacheObject( String key,SoftReference<Object> reference, long expiryTime) {
			this.key=key;
			this.reference=reference;
			this.expiryTime=expiryTime;
		}

		public String getKey() {
			
			return this.key;
		}
		
		public SoftReference<Object> getReference(){
			return this.reference;
			
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			return Longs.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
		}
	}
}