package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.exception.InvalidBlockException;
import com.nbugaenco.blockchain.service.MiningService;
import com.nbugaenco.blockchain.service.implementation.ParallelMiningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockchainTest {

    private Blockchain blockchain;
    private MiningService miningService;

    @BeforeEach
    void setUp() {
        blockchain = Blockchain.withDifficulty(0);
        miningService = new ParallelMiningService();
    }

    @Test
    @DisplayName("Empty blockchain validation")
    void isBlockchainValid_EmptyBlockchain() {
        assertTrue(blockchain.isBlockchainValid());
    }

    @Test
    @DisplayName("Generated blockchain validation")
    void isBlockchainValid_ValidBlockchain() {
        // when
        blockchain = miningService.mineBlocks(blockchain, 2);

        // then
        assertTrue(blockchain.isBlockchainValid());
    }

    @Test
    @DisplayName("Invalid blockchain validation")
    void isBlockchainValid_InvalidBlockchain() {
        // when
        blockchain = miningService.mineBlocks(blockchain, 2);
        blockchain.getLastBlock().setHash("WRONG HASH");

        // then
        InvalidBlockException ex = Assertions.assertThrows(InvalidBlockException.class,
                () -> blockchain.isBlockchainValid());

        assertTrue(ex.getMessage().contains("Invalid block hash"));
    }
}
