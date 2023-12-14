package com.github.murillocg.webcrawler.model;

public final class CrawlCreatedResponse {

    private String id;

    public CrawlCreatedResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
