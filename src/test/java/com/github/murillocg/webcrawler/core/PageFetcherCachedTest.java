package com.github.murillocg.webcrawler.core;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class PageFetcherCachedTest {

    public static final String CACHED_URL = "http://www.cached-url.com";

    public static final String CACHED_PAGE_CONTENT = "cachedContent";

    public static final String NORMAL_URL = "http://www.normal-url.com";

    public static final String NORMAL_PAGE_CONTENT = "pageContent";

    @Test
    public void fetchPageShouldReturnCachedPageWhenUrlIsCached() {
        PageFetcher pageFetcherMock = Mockito.mock(PageFetcher.class);
        Cache cacheMock = Mockito.mock(Cache.class);
        Mockito.when(cacheMock.exists(CACHED_URL)).thenReturn(true);
        Mockito.when(cacheMock.get(CACHED_URL)).thenReturn(CACHED_PAGE_CONTENT);
        PageFetcherCached pageFetcherCached = new PageFetcherCached(pageFetcherMock, cacheMock);

        String actualPage = pageFetcherCached.fetchPage(CACHED_URL);
        assertEquals(CACHED_PAGE_CONTENT, actualPage);
    }

    @Test
    public void fetchPageShouldReturnPageFromPageFetcherWhenUrlIsNotCached() {
        PageFetcher pageFetcherMock = Mockito.mock(PageFetcher.class);
        Cache cacheMock = Mockito.mock(Cache.class);
        Mockito.when(cacheMock.exists(NORMAL_URL)).thenReturn(false);
        Mockito.when(pageFetcherMock.fetchPage(NORMAL_URL)).thenReturn(NORMAL_PAGE_CONTENT);
        PageFetcherCached pageFetcherCached = new PageFetcherCached(pageFetcherMock, cacheMock);

        String actualPage = pageFetcherCached.fetchPage(NORMAL_URL);
        assertEquals(NORMAL_PAGE_CONTENT, actualPage);
    }

}