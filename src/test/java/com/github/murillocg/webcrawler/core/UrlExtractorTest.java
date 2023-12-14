package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.util.FileReader;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlExtractorTest {

    private final UrlExtractor urlExtractor = new UrlExtractor();

    @Test
    public void extractUrlsShouldReturnValidSetWhenContentHasParentRelativeUrlsToExtract() {
        String input = FileReader.read("urlExtractorTest/parentRelativesUrls.html");
        Set<String> urls = urlExtractor.extractUrls("http://hiring.axreng.com/htmlman8/blkid.8.html", input);
        assertFalse(urls.isEmpty());
    }

    @Test
    public void extractUrlsShouldReturnValidSetWhenContentHasRelativeUrlsToExtract() {
        String input = FileReader.read("urlExtractorTest/relativeUrls.html");
        Set<String> urls = urlExtractor.extractUrls("http://hiring.axreng.com/index2.html", input);
        assertFalse(urls.isEmpty());
    }

    @Test
    public void extractUrlsShouldReturnEmptySetWhenPageContentIsNull() {
        Set<String> urls = urlExtractor.extractUrls(null, null);
        assertTrue(urls.isEmpty());
    }

}