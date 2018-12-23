package com.n26.util;
import java.time.Duration;
import java.time.Instant;
// w  w w  .j a v  a 2  s .c om
public class Main {
  public static void main(String[] args) {
    Instant firstInstant = Instant.ofEpochSecond(1294881180); 
    Instant secondInstant = Instant.ofEpochSecond(1294708260); 

    Duration between = Duration.between(firstInstant, secondInstant);

    System.out.println(between.toMillis());
    
    long seconds = between.getSeconds();
    System.out.println(seconds);

    long absoluteResult = between.abs().toMinutes();

  }
}