package com.n26.cache;

import com.google.common.primitives.Longs;
import com.n26.Cache;
import com.n26.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("singleton")
public class TransactionCache implements Cache {

	private final ConcurrentHashMap<Integer, SoftReference<BigDecimal>> cache = new ConcurrentHashMap<>();
	private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();
	private  BigDecimal sum = new BigDecimal("0");
	private  BigDecimal avg = new BigDecimal("0");
	private  BigDecimal max = new BigDecimal("0");
	private  BigDecimal min = new BigDecimal("0");
	private int count=0;

	public TransactionCache() {
		Thread cleanerThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();
					cache.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
					if (delayedCacheObject.getKey()>0) {
						calculate();
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

	@Override
	
	public void add(int key, BigDecimal value, long periodInMillis) {
		if (key <= 0) {
			return;
		}
		if (value == null) {
			cache.remove(key);
		} else {
			long expiryTime = System.currentTimeMillis() + periodInMillis;
			SoftReference<BigDecimal> reference = new SoftReference<>(value);
			cache.put(key, reference);
			cleaningUpQueue.put(new DelayedCacheObject(key, reference, expiryTime));
			calculate();
		}
	}

	private void calculate() {
		sum = new BigDecimal("0.00");
		avg= new BigDecimal("0.00");
		min=new BigDecimal("0.00");
		max=new BigDecimal("0.00");
		BigDecimal tempValue=new BigDecimal("0");
		count=0;
		for (int key:cache.keySet()) {
			tempValue=cache.get(key).get();
			sum=sum.add(tempValue);
			
			
			if ( (min.compareTo(tempValue))>0|| min.equals(new BigDecimal("0.00"))) {
				min=tempValue;				
			}
			if  (max.compareTo(tempValue)<0) {
				max=tempValue;				
			}
			count++;
		}
		if (count>0) {
		avg=sum.divide(new BigDecimal(count),2,RoundingMode.HALF_UP);
		}
		
		
	}

	@Override
	public void remove(int key) {
		cache.remove(key);
	}

	@Override
	public Object get(int key) {
		return Optional.ofNullable(cache.get(key)).map(SoftReference::get).orElse(null);
	}

	@Override
	public void clear() {
		cache.clear();
		calculate();
	}

	@Override
	public long size() {
		return cache.size();
	}

	public BigDecimal getSum() {

		return this.sum.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public BigDecimal getAvg() {

		return this.avg.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public BigDecimal getMax() {

		return this.max.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
	
	public BigDecimal getMin() {

		return this.min.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
	public long getCount() {
		
		return Long.valueOf(this.count);
	}

	@EqualsAndHashCode
	private static class DelayedCacheObject implements Delayed {

		private final int key;

		private final SoftReference<BigDecimal> reference;
		private final long expiryTime;

		private DelayedCacheObject(int key, SoftReference<BigDecimal> reference, long expiryTime) {
			this.key = key;
			this.reference = reference;
			this.expiryTime = expiryTime;
		}

		public int getKey() {

			return this.key;
		}

		public SoftReference<BigDecimal> getReference() {
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