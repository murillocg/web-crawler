package com.github.murillocg.webcrawler.model;

public final class ErrorResponse {

    private final String message;

    public ErrorResponse(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

}
