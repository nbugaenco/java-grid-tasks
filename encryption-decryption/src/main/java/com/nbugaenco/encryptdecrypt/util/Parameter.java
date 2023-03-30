package com.nbugaenco.encryptdecrypt.util;

import com.nbugaenco.encryptdecrypt.model.ShiftEncoder;
import com.nbugaenco.encryptdecrypt.model.UnicodeEncoder;

import java.util.Arrays;

/**
 * Parameters that used in CLI args for encoding-decoding process
 */
public enum Parameter {
    /**
     * Can be {@code enc} or {@code dec}
     */
    MODE("-mode"),
    /**
     * Must be an integer number
     */
    KEY("-key"),
    /**
     * Can be {@code shift} ({@link ShiftEncoder}) or {@code unicode} ({@link UnicodeEncoder})
     */
    ALG("-alg"),
    /**
     * Any {@link String}
     */
    DATA("-data"),
    /**
     * File path from which data will be read
     */
    IN("-in"),
    /**
     * File path to which data will be written
     */
    OUT("-out"),
    /**
     * Parameter for printing help message
     */
    HELP("-help");

    final String label;

    Parameter(String label) {
        this.label = label;
    }

    public static Parameter valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(it -> it.label.equals(label))
                .findFirst().orElse(null);
    }
}
