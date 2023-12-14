package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.model.CrawlResult;
import com.github.murillocg.webcrawler.model.CrawlStatus;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class CrawlResultRepository {

    private final Map<String, CrawlResult> crawlResultLocalDb = new ConcurrentHashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final CodeGenerator codeGenerator = new CodeGenerator();

    public CrawlResult saveAsActive(String keyword) {
        Objects.requireNonNull(keyword, "Keyword must not be null");
        lock.writeLock().lock();
        try {
            CrawlResult crawlResult = new CrawlResult();
            String queryId = codeGenerator.generate();
            crawlResult.setId(queryId);
            crawlResult.setStatus(CrawlStatus.ACTIVE);
            crawlResult.setKeyword(keyword);
            crawlResultLocalDb.put(queryId, crawlResult);
            return crawlResult;
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void addUrlToCrawlResult(String crawlId, String url) {
        Objects.requireNonNull(url, "Url must not be null");
        if (!crawlResultLocalDb.containsKey(crawlId)) {
            throw new IllegalArgumentException("CrawlResult not found for id:" + crawlId);
        }
        lock.writeLock().lock();
        try {
            CrawlResult current = crawlResultLocalDb.get(crawlId);
            current.addUrl(url);
            crawlResultLocalDb.put(crawlId, current);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void saveAsDone(String crawlId) {
        if (!crawlResultLocalDb.containsKey(crawlId)) {
            throw new IllegalArgumentException("CrawlResult not found for id:" + crawlId);
        }
        lock.writeLock().lock();
        try {
            CrawlResult current = crawlResultLocalDb.get(crawlId);
            current.setStatus(CrawlStatus.DONE);
            crawlResultLocalDb.put(crawlId, current);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public Optional<CrawlResult> findById(String id) {
        if (!crawlResultLocalDb.containsKey(id)) {
            return Optional.empty();
        }
        lock.readLock().lock();
        try {
            return Optional.of(crawlResultLocalDb.get(id));
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public int countUrlsById(String crawlId) {
        if (!crawlResultLocalDb.containsKey(crawlId)) {
            return 0;
        }
        lock.readLock().lock();
        try {
            CrawlResult current = crawlResultLocalDb.get(crawlId);
            return current.getUrls().size();
        } finally {
            lock.readLock().unlock();
        }
    }

}
