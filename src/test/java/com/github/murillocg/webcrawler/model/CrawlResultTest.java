package com.github.murillocg.webcrawler.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlResultTest {

    @Test
    public void addUrlShouldThrowExceptionWhenCalledOutsideTheObject() {
        CrawlResult crawlResult = new CrawlResult();
        crawlResult.setId("1");
        crawlResult.addUrl("http://www.1.com");

        List<String> unmodifiableUrls = crawlResult.getUrls();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableUrls.add("http://www.2.com"));
    }

    @Test
    public void addUrlShouldAddWithSuccessWhenUrlIsNotNull() {
        CrawlResult crawlResult = new CrawlResult();
        crawlResult.setId("1");
        crawlResult.addUrl("http://www.1.com");
        crawlResult.addUrl("http://www.3.com");

        assertEquals(2, crawlResult.getUrls().size());
    }

    @Test
    public void addUrlShouldThrowExceptionWhenUrlIsNull() {
        CrawlResult crawlResult = new CrawlResult();
        crawlResult.setId("1");

        assertThrows(NullPointerException.class, () -> crawlResult.addUrl(null));
    }

}