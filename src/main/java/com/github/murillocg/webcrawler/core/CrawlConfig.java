package com.github.murillocg.webcrawler.core;

import java.util.Objects;

public final class CrawlConfig {

    private int maxResultMatches = 100;

    private int crawlerTaskThreadPoolSize = 200;

    private String baseUrl;

    public void validate() {
        Objects.requireNonNull(baseUrl, "Base URL must not be null");
        if (maxResultMatches < 1) {
            throw new IllegalArgumentException("Maximum result matches must not be less than 1");
        }
        if (crawlerTaskThreadPoolSize < 1) {
            throw new IllegalArgumentException("ThreadPoolSize for crawler tasks must not be less than 1");
        }
    }

    public void setMaxResultMatches(int maxResultMatches) {
        this.maxResultMatches = maxResultMatches;
    }

    public void setCrawlerTaskThreadPoolSize(int crawlerTaskThreadPoolSize) {
        this.crawlerTaskThreadPoolSize = crawlerTaskThreadPoolSize;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getMaxResultMatches() {
        return maxResultMatches;
    }

    public int getCrawlerTaskThreadPoolSize() {
        return crawlerTaskThreadPoolSize;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}
