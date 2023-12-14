package com.github.murillocg.webcrawler.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryCacheTest {

    private MemoryCache memoryCache = new MemoryCache();

    @BeforeEach
    public void setUp() {
        memoryCache = new MemoryCache();
    }

    @Test
    public void existsShouldReturnFalseWhenKeyDoesNotExist() {
        boolean actual = memoryCache.exists("doesNotExist");
        assertFalse(actual);
    }

    @Test
    public void existsShouldReturnTrueWhenKeyExists() {
        String key = "validKey";
        memoryCache.store(key, "content");
        boolean actual = memoryCache.exists(key);
        assertTrue(actual);
    }

    @Test
    public void getShouldReturnContentWhenKeyIsStored() {
        String key = "validKey";
        memoryCache.store(key, "content");
        String actualContent = memoryCache.get(key);
        assertNotNull(actualContent);
    }

}