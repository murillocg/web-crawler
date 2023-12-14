package com.github.murillocg.webcrawler.core;

import java.util.Locale;
import java.util.Objects;

public final class ContentFinder {

    public boolean findKeyword(String content, String keyword) {
        Objects.requireNonNull(content, "Content must not be null");
        Objects.requireNonNull(content, "Keyword must not be null");
        return content.toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH));
    }

}
