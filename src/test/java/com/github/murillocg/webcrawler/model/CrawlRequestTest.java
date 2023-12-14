package com.github.murillocg.webcrawler.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlRequestTest {

    private static final String DEFAULT_KEYWORD = "default";

    @Test
    public void setKeywordShouldSetProperlyWhenCalled() {
        CrawlRequest request = new CrawlRequest();
        request.setKeyword(DEFAULT_KEYWORD);
        assertEquals(DEFAULT_KEYWORD, request.getKeyword());
    }

    @Test
    public void validateShouldExecuteWithSuccessWhenKeywordValid() {
        CrawlRequest request = new CrawlRequest();
        request.setKeyword(DEFAULT_KEYWORD);
        assertDoesNotThrow(() -> request.validate());
    }

    @Test
    public void validateShouldThrowExceptionWhenKeywordIsNull() {
        CrawlRequest request = new CrawlRequest();
        request.setKeyword(null);
        assertThrows(IllegalArgumentException.class, () -> request.validate());
    }

    @Test
    public void validateShouldThrowExceptionWhenKeywordIsTooSmall() {
        CrawlRequest request = new CrawlRequest();
        request.setKeyword("tes");
        assertThrows(IllegalArgumentException.class, () -> request.validate());
    }

    @Test
    public void validateShouldThrowExceptionWhenKeywordIsTooBig() {
        CrawlRequest request = new CrawlRequest();
        request.setKeyword("123456789X123456789X123456789X123");
        assertThrows(IllegalArgumentException.class, () -> request.validate());
    }

}