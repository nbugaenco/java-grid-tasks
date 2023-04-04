package com.nbugaenco.encryptdecrypt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShiftEncoderTest {

    Encoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new ShiftEncoder();
        encoder.setKey(5);
    }

    @Test
    void testValidEncode() {
        // given
        encoder.setPrompt("Hello World!");

        // when then
        Assertions.assertEquals("Mjqqt Btwqi!", encoder.encode());
    }

    @Test
    void testValidDencode() {
        // given
        encoder.setPrompt("Mjqqt Btwqi!");

        // when then
        Assertions.assertEquals("Hello World!", encoder.decode());
    }
}
