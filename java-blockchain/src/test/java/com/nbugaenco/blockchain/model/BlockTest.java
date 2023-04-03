package com.nbugaenco.blockchain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockTest {

    Block block;

    @BeforeEach
    void setUp() {
        block = Block.create("0", 1);
    }

    @Test
    @DisplayName("Get transactions of empty block")
    void getBlockMessages_EmptyBlock() {
        assertTrue(block.toString().contains("no transactions"));
    }
}
