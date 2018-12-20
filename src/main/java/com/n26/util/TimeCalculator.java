package com.n26.util;

import java.time.Duration;
import java.time.Instant;

public class TimeCalculator {

	public static Long instantDifference(Instant instant)
	{
		 Duration between = Duration.between(instant, Instant.now());	
		
		return between.toMillis();

	}
}
