package com.github.murillocg.webcrawler.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeGeneratorTest {

    private final CodeGenerator codeGenerator = new CodeGenerator();

    @Test
    public void generateShouldGenerateAValueWhenCalled() {
        String actual = codeGenerator.generate();
        Assertions.assertNotNull(actual);
    }

    @Test
    public void generateShouldGenerateEightCharsLengthWhenCalled() {
        String actual = codeGenerator.generate();
        assertEquals(8, actual.length());
    }

}