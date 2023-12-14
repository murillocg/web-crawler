package com.github.murillocg.webcrawler;

import com.github.murillocg.webcrawler.core.*;
import com.github.murillocg.webcrawler.model.*;
import com.google.gson.Gson;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.*;

public class Main {

    private static final String BASE_URL = System.getenv("BASE_URL");

    public static void main(String[] args) {
        final CrawlService crawlService = buildCrawlServiceInstance();

        get("/crawl/:id", (req, res) -> {
            String id = req.params("id");

            Optional<CrawlResult> optionalQueryResult = crawlService.checkCrawlStatus(id);
            if (!optionalQueryResult.isPresent()) {
                res.status(404);
                return "";
            }
            CrawlStatusResponse response = new CrawlStatusResponse(optionalQueryResult.get());
            return new Gson().toJson(response);
        });
        post("/crawl", (req, res) -> {
            CrawlRequest crawlRequest = new Gson().fromJson(req.body(), CrawlRequest.class);
            crawlRequest.validate();

            CrawlResult crawlResult = crawlService.createCrawl(crawlRequest.getKeyword());

            CrawlCreatedResponse crawlCreatedResponse = new CrawlCreatedResponse(crawlResult.getId());
            return new Gson().toJson(crawlCreatedResponse);
        });

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(new Gson().toJson(new ErrorResponse(e)));
        });
    }

    private static CrawlService buildCrawlServiceInstance() {
        final CrawlConfig config = new CrawlConfig();
        config.setBaseUrl(BASE_URL);
        config.validate();

        final PageFetcher pageFetcher = new PageFetcherCached(new NativePageFetcher(), new MemoryCache());
        final ContentFinder contentFinder = new ContentFinder();
        final UrlExtractor urlExtractor = new UrlExtractor();
        final ExecutorService executor = Executors.newFixedThreadPool(config.getCrawlerTaskThreadPoolSize());

        final CrawlService crawlService = new CrawlService(new CrawlResultRepository(), config, pageFetcher,
                contentFinder, urlExtractor, executor);
        return crawlService;
    }

}
