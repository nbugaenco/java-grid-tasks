package com.nbugaenco.encryptdecrypt.model;

/**
 * This {@link Encoder} uses shift method.
 * It's only changing english letters inside the alphabet.
 * For example: {@code -key 5 -data "Hello World!"} will give {@code "Mjqqt Btwqi!"}
 */
public class ShiftEncoder extends Encoder {

    public ShiftEncoder() {
        super();
    }

    private Integer encodeChar(Integer c) {
        if (c >= 65 && c <= 90) {
            c = (c + key > 90) ? (c + key) - 26 : c + key;
        } else if (c >= 97 && c <= 122) {
            c = (c + key > 122) ? (c + key) - 26 : c + key;
        }

        return c;
    }

    private Integer decodeChar(Integer c) {
        if (c >= 65 && c <= 90) {
            c = (c - key < 65) ? (c - key) + 26 : c - key;
        } else if (c >= 97 && c <= 122) {
            c = (c - key < 97) ? (c - key) + 26 : c - key;
        }

        return c;
    }

    @Override
    public String encode() {
        return prompt.codePoints()
                .map(this::encodeChar)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    @Override
    public String decode() {
        return prompt.codePoints()
                .map(this::decodeChar)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
