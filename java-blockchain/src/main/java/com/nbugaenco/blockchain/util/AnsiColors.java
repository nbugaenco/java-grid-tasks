package com.nbugaenco.blockchain.util;

/**
 * Enum representing various ANSI color codes and formatting options.
 * These can be used to style text output in the terminal.
 *
 * @author nbugaenco
 */
public enum AnsiColors {
    RESET("\u001B[0m"),

    // Formatting
    BOLD("\u001B[1m"),
    ITALIC("\u001B[3m"),
    UNDERLINE("\u001B[4m"),

    // Text colors
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m");

    final String value;

    /**
     * Constructs an AnsiColors instance with the given ANSI code.
     *
     * @param value the ANSI code representing the color or formatting option
     */
    AnsiColors(String value) {
        this.value = value;
    }

    /**
     * Returns the ANSI code of this AnsiColors instance as a string.
     *
     * @return the ANSI code as a string
     */
    @Override
    public String toString() {
        return value;
    }
}
