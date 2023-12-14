package com.github.murillocg.webcrawler.model;

import java.util.Objects;

public class CrawlRequest {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void validate() {
        if (keyword == null || keyword.length() < 4 || keyword.length() > 32) {
            throw new IllegalArgumentException("Keyword not informed or not valid!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawlRequest that = (CrawlRequest) o;
        return Objects.equals(keyword, that.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword);
    }

}
