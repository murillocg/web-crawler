package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.model.CrawlResult;
import com.github.murillocg.webcrawler.model.CrawlStatus;
import com.github.murillocg.webcrawler.util.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class CrawlerTaskTest {

    private static final String BASE_URL = "http://hiring.axreng.com/";

    private CrawlResultRepository crawlResultRepository;

    @BeforeEach
    public void cleanup() {
        crawlResultRepository = new CrawlResultRepository();
    }

    //TODO: Create one more test
    @Test
    public void runShouldExecuteAndSaveOneCrawlResultWithOnlyOneUrlFoundWhenCalled() {
        CrawlConfig config = new CrawlConfig();
        config.setBaseUrl(BASE_URL);

        NativePageFetcher nativePageFetcherMock = Mockito.mock(NativePageFetcher.class);
        String fetchPageContent = FileReader.read("crawlerTaskTest/securityInContent.html");
        Mockito.when(nativePageFetcherMock.fetchPage(BASE_URL)).thenReturn(fetchPageContent);

        UrlExtractor urlExtractorMock = Mockito.mock(UrlExtractor.class);
        Mockito.when(urlExtractorMock.extractUrls(BASE_URL, fetchPageContent)).thenReturn(Collections.emptySet());

        CrawlResult crawlResult = crawlResultRepository.saveAsActive("security");

        CrawlerTask crawlerTask = new CrawlerTask(config, nativePageFetcherMock, new ContentFinder(), crawlResult,
                crawlResultRepository, urlExtractorMock);

        crawlerTask.run();

        Optional<CrawlResult> crawlResultOptional = crawlResultRepository.findById(crawlResult.getId());
        Assertions.assertFalse(crawlResultOptional.isEmpty());
        crawlResult = crawlResultOptional.get();
        Assertions.assertEquals(CrawlStatus.DONE, crawlResult.getStatus());
        Assertions.assertTrue(crawlResult.getUrls().contains(BASE_URL));
    }

}