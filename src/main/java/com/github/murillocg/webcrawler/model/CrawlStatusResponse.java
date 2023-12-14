package com.github.murillocg.webcrawler.model;

import java.util.List;

public final class CrawlStatusResponse {

    private String id;

    private CrawlStatus status;

    private List<String> urls;

    public CrawlStatusResponse(CrawlResult crawlResult) {
        this.setId(crawlResult.getId());
        this.setStatus(crawlResult.getStatus());
        this.setUrls(crawlResult.getUrls());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
