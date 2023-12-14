package com.github.murillocg.webcrawler.core;

public class PageFetcherCached implements PageFetcher {

    private final PageFetcher pageFetcher;

    private final Cache cache;

    public PageFetcherCached(PageFetcher pageFetcher, Cache cache) {
        this.pageFetcher = pageFetcher;
        this.cache = cache;
    }

    @Override
    public String fetchPage(String url) {
        if (cache.exists(url)) {
            return cache.get(url);
        }
        String content = pageFetcher.fetchPage(url);
        cache.store(url, content);
        return content;
    }

}
