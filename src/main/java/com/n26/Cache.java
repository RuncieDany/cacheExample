package com.n26;

import java.math.BigDecimal;

public interface Cache {
    
    void add(int key, BigDecimal value, long periodInMillis);
 
    void remove(int key);
 
    Object get(int key);
 
    void clear();
 
    long size();
}