package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.model.CrawlResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CrawlerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerTask.class);

    private final CrawlConfig config;

    private final PageFetcher pageFetcher;

    private final ContentFinder contentFinder;

    private final CrawlResult crawlResult;

    private final CrawlResultRepository crawlResultRepository;

    private final UrlExtractor urlExtractor;

    public CrawlerTask(CrawlConfig config, PageFetcher pageFetcher, ContentFinder contentFinder, CrawlResult crawlResult,
                       CrawlResultRepository crawlResultRepository, UrlExtractor urlExtractor) {
        this.config = config;
        this.pageFetcher = pageFetcher;
        this.contentFinder = contentFinder;
        this.crawlResult = crawlResult;
        this.crawlResultRepository = crawlResultRepository;
        this.urlExtractor = urlExtractor;
    }

    @Override
    public void run() {
        final Set<String> crawledLinks = new HashSet<>();
        final Queue<String> linksToCrawl = new LinkedList<>();

        LOGGER.info("Crawl started for keyword {}!", crawlResult.getKeyword());
        linksToCrawl.add(config.getBaseUrl());
        while (!linksToCrawl.isEmpty()) {
            String currentUrl = linksToCrawl.remove();
            if (crawledLinks.contains(currentUrl)) {
                continue;
            }

            LOGGER.info("Crawling URL {}...", currentUrl);
            String pageContent = pageFetcher.fetchPage(currentUrl);

            boolean keywordFound = contentFinder.findKeyword(pageContent, crawlResult.getKeyword());
            if (keywordFound) {
                LOGGER.info("Keyword \"{}\" found at URL {}.", crawlResult.getKeyword(), currentUrl);
                crawlResultRepository.addUrlToCrawlResult(crawlResult.getId(), currentUrl);
            }
            if (isMaxResultsReached(crawlResultRepository.countUrlsById(crawlResult.getId()))) {
                LOGGER.info("Maximum of {} results for keyword \"{}\" has reached!", config.getMaxResultMatches(), crawlResult.getKeyword());
                break;
            }

            Set<String> childUrls = urlExtractor.extractUrls(currentUrl, pageContent);
            linksToCrawl.addAll(childUrls);

            crawledLinks.add(currentUrl);
        }

        LOGGER.info("Crawl finished for \"keyword\" {}!", crawlResult.getKeyword());
        crawlResultRepository.saveAsDone(crawlResult.getId());
    }

    private boolean isMaxResultsReached(int urlsCount) {
        return config.getMaxResultMatches() == urlsCount;
    }

}
