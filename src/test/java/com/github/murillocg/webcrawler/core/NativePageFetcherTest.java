package com.github.murillocg.webcrawler.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NativePageFetcherTest {

    private final NativePageFetcher nativePageFetcher = new NativePageFetcher();

    @Test
    public void fetchPageShouldReturnContentWhenUrlIsValid() {
        String fetchedPage = nativePageFetcher.fetchPage("http://hiring.axreng.com/");
        assertNotNull(fetchedPage);
    }

    @Test
    public void fetchPageShouldThrowExceptionWhenUrlIsInvalid() {
        assertThrows(RuntimeException.class, () -> nativePageFetcher.fetchPage("http://invalid.url.com/"));
    }

}