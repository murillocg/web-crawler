package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.model.CrawlResult;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class CrawlService {

    private final CrawlResultRepository crawlResultRepository;

    private final CrawlConfig crawlConfig;

    private final PageFetcher pageFetcher;

    private final ContentFinder contentFinder;

    private final UrlExtractor urlExtractor;

    private final ExecutorService executor;

    public CrawlService(CrawlResultRepository crawlResultRepository, CrawlConfig crawlConfig, PageFetcher pageFetcher,
                        ContentFinder contentFinder, UrlExtractor urlExtractor, ExecutorService executor) {
        this.crawlResultRepository = crawlResultRepository;
        this.crawlConfig = crawlConfig;
        this.pageFetcher = pageFetcher;
        this.contentFinder = contentFinder;
        this.urlExtractor = urlExtractor;
        this.executor = executor;
    }

    public CrawlResult createCrawl(String keyword) {
        CrawlResult crawlResult = crawlResultRepository.saveAsActive(keyword);

        executor.execute(new CrawlerTask(crawlConfig, pageFetcher, contentFinder, crawlResult, crawlResultRepository,
                urlExtractor));

        return crawlResult;
    }

    public Optional<CrawlResult> checkCrawlStatus(String id) {
        return crawlResultRepository.findById(id);
    }

}
