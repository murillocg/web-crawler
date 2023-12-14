package com.github.murillocg.webcrawler.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class CrawlResult {

    private String id;

    private CrawlStatus status;

    private String keyword;

    private List<String> urls = new LinkedList<>();

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
        return Collections.unmodifiableList(urls);
    }

    public void addUrl(String url) {
        Objects.requireNonNull(url, "Url must not be null");
        this.urls.add(url);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawlResult that = (CrawlResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
