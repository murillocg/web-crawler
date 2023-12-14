package com.github.murillocg.webcrawler.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlExtractor {

    private final Pattern linkPattern = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);

    public Set<String> extractUrls(String currentUrl, String pageContent) {
        if (pageContent == null) {
            return Collections.emptySet();
        }
        List<String> urls = new LinkedList<>();

        Matcher m = linkPattern.matcher(pageContent);
        while (m.find()) {
            urls.add(m.group(1));
        }

        URI currentUri;
        try {
            currentUri = new URI(currentUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        URI finalCurrentUri = currentUri;

        //Remove external links. Urls starting with https also are included here.
        urls.removeIf(u -> u.startsWith("http") && !u.contains(finalCurrentUri.getHost()));

        //Remove other resources such as .ico, .css
        urls.removeIf(u -> !u.endsWith("html"));

        urls.removeIf(u -> u.equals("index.html"));

        //Resolve relative urls such as "htmlman2/bind.2.html" and "../htmlman5/slapd.html"
        urls = urls.stream()
                .map(u -> finalCurrentUri.resolve(u).toString())
                .collect(Collectors.toList());

        return new HashSet<>(urls);
    }
}
