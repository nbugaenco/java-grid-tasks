package com.nbugaenco.encryptdecrypt;

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
