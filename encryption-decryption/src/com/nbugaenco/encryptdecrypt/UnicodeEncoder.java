package com.nbugaenco.encryptdecrypt;

/**
 * This {@link Encoder} uses unicode method.
 * It's changing all characters based on {@code -key} value
 * For example: {@code -key 10 -alg unicode -data "Hello World!"} will give {@code "Rovvy*ay|vn+"}
 */
public class UnicodeEncoder extends Encoder {

    public UnicodeEncoder() {
    }

    @Override
    public String encode() {
        return prompt.codePoints()
                .map(c -> (char) (c + key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    @Override
    public String decode() {
        return prompt.codePoints()
                .map(c -> (char) (c - key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
