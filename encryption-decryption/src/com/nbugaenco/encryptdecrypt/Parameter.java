package com.nbugaenco.encryptdecrypt;

public enum Parameter {
    MODE("-mode"),
    KEY("-key"),
    ALG("-alg"),
    DATA("-data"),
    IN("-in"),
    OUT("-out");

    final String label;

    Parameter(String label) {
        this.label = label;
    }

    public static Parameter valueOfLabel(String label) {
        for (Parameter e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
