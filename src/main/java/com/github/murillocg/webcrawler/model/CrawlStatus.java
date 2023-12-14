package com.github.murillocg.webcrawler.model;

import com.google.gson.annotations.SerializedName;

public enum CrawlStatus {

    @SerializedName("active")
    ACTIVE,

    @SerializedName("done")
    DONE;

}
