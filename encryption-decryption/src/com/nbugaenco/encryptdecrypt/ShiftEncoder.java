package com.nbugaenco.encryptdecrypt;

public class ShiftEncoder extends Encoder {

    public ShiftEncoder() {
    }

    @Override
    public String encode() {
        return prompt.codePoints()
                .map(c -> {
                    if (c >= 65 && c <= 90) {
                        c = (c + key > 90) ? (c + key) - 26 : c + key;
                    } else if (c >= 97 && c <= 122) {
                        c = (c + key > 122) ? (c + key) - 26 : c + key;
                    }

                    return c;
                })
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    @Override
    public String decode() {
        return prompt.codePoints()
                .map(c -> {
                    if (c >= 65 && c <= 90) {
                        c = (c - key < 65) ? (c - key) + 26 : c - key;
                    } else if (c >= 97 && c <= 122) {
                        c = (c - key < 97) ? (c - key) + 26 : c - key;
                    }

                    return c;
                })
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
