package com.github.murillocg.webcrawler.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlConfigTest {

    @Test
    public void validateShouldNotThrowExceptionWhenHasAValidConfiguration() {
        CrawlConfig config = new CrawlConfig();
        config.setBaseUrl("http://www.mytest.com");
        assertDoesNotThrow(config::validate);
    }

    @Test
    public void validateShouldThrowExceptionWhenDoesNotHaveAValidBaseUrl() {
        CrawlConfig config = new CrawlConfig();
        assertThrows(NullPointerException.class, config::validate);
    }

    @Test
    public void validateShouldThrowExceptionWhenDoesNotHaveAValidMaxResultMatchesValue() {
        CrawlConfig config = new CrawlConfig();
        config.setBaseUrl("http://www.mytest.com");
        config.setMaxResultMatches(0);
        assertThrows(IllegalArgumentException.class, config::validate);
    }

    @Test
    public void validateShouldThrowExceptionWhenDoesNotHaveAValidCrawlerTaskThreadPoolSize() {
        CrawlConfig config = new CrawlConfig();
        config.setBaseUrl("http://www.mytest.com");
        config.setCrawlerTaskThreadPoolSize(0);
        assertThrows(IllegalArgumentException.class, config::validate);
    }

}