package com.nbugaenco.encryptdecrypt.service;

import com.nbugaenco.encryptdecrypt.model.Encoder;
import com.nbugaenco.encryptdecrypt.model.ShiftEncoder;
import com.nbugaenco.encryptdecrypt.model.UnicodeEncoder;
import com.nbugaenco.encryptdecrypt.util.Parameter;

/**
 * This factory creates {@link Encoder} based on {@link Parameter#ALG}
 */
public class EncoderFactory {

    public Encoder create(String alg) {
        switch (alg) {
            case "shift" -> {
                return new ShiftEncoder();
            }
            case "unicode" -> {
                return new UnicodeEncoder();
            }
            default -> throw new IllegalArgumentException("Wrong algorithm: " + "\"" + alg + "\"");
        }
    }
}
