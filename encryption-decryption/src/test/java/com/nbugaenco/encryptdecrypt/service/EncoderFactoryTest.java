package com.nbugaenco.encryptdecrypt.service;

import com.nbugaenco.encryptdecrypt.model.Encoder;
import com.nbugaenco.encryptdecrypt.model.ShiftEncoder;
import com.nbugaenco.encryptdecrypt.model.UnicodeEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncoderFactoryTest {

    EncoderFactory encoderFactory;

    @BeforeEach
    void setUp() {
        encoderFactory = new EncoderFactory();
    }

    @Test
    void createShiftEncoder() {
        // when
        Encoder encoder = encoderFactory.create("shift");

        // then
        assertTrue(encoder instanceof ShiftEncoder);
    }

    @Test
    void createUnicodeEncoder() {
        // when
        Encoder encoder = encoderFactory.create("unicode");

        // then
        assertTrue(encoder instanceof UnicodeEncoder);
    }

    @Test
    void createInvalidEncoder() {
        // when
        IllegalArgumentException ex =
            assertThrows(IllegalArgumentException.class, () -> encoderFactory.create("invalid"));

        // then
        assertTrue(ex.getMessage().contains("Wrong algorithm"));
    }
}
