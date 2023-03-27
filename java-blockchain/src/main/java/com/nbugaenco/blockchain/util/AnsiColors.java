package com.nbugaenco.blockchain.util;

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

    AnsiColors(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
