package com.github.murillocg.webcrawler.core;

import com.github.murillocg.webcrawler.util.FileReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContentFinderTest {

    private final ContentFinder contentFinder = new ContentFinder();

    @Test
    public void findKeywordShouldReturnTrueWhenContentIsLowerCase() {
        String content = FileReader.read("contentFinderTest/securityLowerCaseContent.html");
        boolean actual = contentFinder.findKeyword(content, "security");
        assertTrue(actual);
    }

    @Test
    public void findKeywordShouldReturnTrueWhenContentIsUpperCase() {
        String content = FileReader.read("contentFinderTest/securityUpperCaseContent.html");
        boolean actual = contentFinder.findKeyword(content, "security");
        assertTrue(actual);
    }

    @Test
    public void findKeywordShouldReturnTrueWhenKeywordIsUpperCase() {
        String content = FileReader.read("contentFinderTest/securityLowerCaseContent.html");
        boolean actual = contentFinder.findKeyword(content, "SECURITY");
        assertTrue(actual);
    }

    @Test
    public void findKeywordShouldReturnFalseWhenKeywordIsNotFound() {
        String content = FileReader.read("contentFinderTest/securityLowerCaseContent.html");
        boolean actual = contentFinder.findKeyword(content, "keywordDoesNotExist");
        assertFalse(actual);
    }

    @Test
    public void findKeywordShouldExceptionWhenContentIsNull() {
        assertThrows(NullPointerException.class,
                () -> contentFinder.findKeyword(null, "security"));
    }

    @Test
    public void findKeywordShouldReturnTrueWhenKeywordIsInComments() {
        String content = FileReader.read("contentFinderTest/securityInCommentsContent.html");
        boolean actual = contentFinder.findKeyword(content, "security");
        assertTrue(actual);
    }

    @Test
    public void findKeywordShouldReturnTrueWhenKeywordIsInTags() {
        String content = FileReader.read("contentFinderTest/securityLowerCaseContent.html");
        boolean actual = contentFinder.findKeyword(content, "title");
        assertTrue(actual);
    }

}