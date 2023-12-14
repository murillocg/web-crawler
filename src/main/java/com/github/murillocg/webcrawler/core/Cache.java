package com.github.murillocg.webcrawler.core;

public interface Cache {
    boolean exists(String key);
    String get(String key);
    void store(String key, String data);
}
