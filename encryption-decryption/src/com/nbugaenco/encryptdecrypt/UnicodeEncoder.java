package com.nbugaenco.encryptdecrypt;

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
