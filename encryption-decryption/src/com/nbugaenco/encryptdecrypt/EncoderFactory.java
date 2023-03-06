package com.nbugaenco.encryptdecrypt;

public class EncoderFactory {

    public Encoder create(String alg) {
        switch (alg) {
            case "shift" -> {
                return new ShiftEncoder();
            }
            case "unicode" -> {
                return new UnicodeEncoder();
            }
            default -> {
                return null;
            }
        }
    }
}
