package com.github.murillocg.webcrawler.util;

import java.io.IOException;

public final class FileReader {

    public static String read(String filePath) {
        try {
            return new String(FileReader.class.getClassLoader().getResourceAsStream(filePath).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
