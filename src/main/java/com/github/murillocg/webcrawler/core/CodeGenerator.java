package com.github.murillocg.webcrawler.core;

public final class CodeGenerator {

    private static final String ALPHANUMERIC_CHARS = "abcdefghijklmnopqrstuvxyz0123456789";

    private static final int CODE_LENGTH = 8;

    public String generate() {
        StringBuilder finalCode = new StringBuilder(CODE_LENGTH);

        for (int i=0; i<CODE_LENGTH; i++) {
            int charIndex = (int)(ALPHANUMERIC_CHARS.length() * Math.random());
            finalCode.append(ALPHANUMERIC_CHARS.charAt(charIndex));
        }

        return finalCode.toString();
    }

}
