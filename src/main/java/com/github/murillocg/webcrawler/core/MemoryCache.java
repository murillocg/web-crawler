package com.github.murillocg.webcrawler.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MemoryCache implements Cache {

    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public boolean exists(String key) {
        return cache.containsKey(key);
    }

    @Override
    public String get(String key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void store(String key, String data) {
        lock.writeLock().lock();
        try {
            cache.put(key, data);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

}
