package com.nbugaenco.encryptdecrypt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnicodeEncoderTest {

    Encoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new UnicodeEncoder();
        encoder.setKey(10);
    }

    @Test
    void testValidEncode() {
        // given
        encoder.setPrompt("Hello World!");

        // when then
        Assertions.assertEquals("Rovvy*ay|vn+", encoder.encode());
    }

    @Test
    void testValidDencode() {
        // given
        encoder.setPrompt("Rovvy*ay|vn+");

        // when then
        Assertions.assertEquals("Hello World!", encoder.decode());
    }
}
