package com.n26.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class CacheKeyGenerator {

	private static AtomicInteger atomicKey = new AtomicInteger(0);

	public static  int getKey() {
		return atomicKey.incrementAndGet();
	}
}
