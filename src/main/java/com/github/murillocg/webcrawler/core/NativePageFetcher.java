package com.github.murillocg.webcrawler.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NativePageFetcher implements PageFetcher {

    @Override
    public String fetchPage(String url) {
        try {
            URL urlToFetch = new URI(url).toURL();
            try (InputStream in = urlToFetch.openStream()) {
                return new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
