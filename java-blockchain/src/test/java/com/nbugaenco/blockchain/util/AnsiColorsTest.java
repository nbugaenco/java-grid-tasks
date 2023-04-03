package com.nbugaenco.blockchain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnsiColorsTest {

    @Test
    @DisplayName("Ansi colors constants validity")
    void ansiColorsValues() {
        assertEquals("\u001B[0m", AnsiColors.RESET.toString());
        assertEquals("\u001B[1m", AnsiColors.BOLD.toString());
        assertEquals("\u001B[3m", AnsiColors.ITALIC.toString());
        assertEquals("\u001B[4m", AnsiColors.UNDERLINE.toString());
        assertEquals("\u001B[31m", AnsiColors.RED.toString());
        assertEquals("\u001B[32m", AnsiColors.GREEN.toString());
        assertEquals("\u001B[33m", AnsiColors.YELLOW.toString());
        assertEquals("\u001B[34m", AnsiColors.BLUE.toString());
        assertEquals("\u001B[35m", AnsiColors.MAGENTA.toString());
        assertEquals("\u001B[36m", AnsiColors.CYAN.toString());
    }
}
