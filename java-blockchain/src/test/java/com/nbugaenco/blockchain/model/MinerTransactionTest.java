package com.nbugaenco.blockchain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinerTransactionTest {

    List<MinerThread> miners;

    @BeforeEach
    void setUp() {
        miners = new ArrayList<>();

        Blockchain blockchain = Blockchain.withDifficulty(0);
        miners.add(new MinerThread(blockchain, 100, 1));
        miners.add(new MinerThread(blockchain, 100, 2));
    }

    @Test
    @DisplayName("Transaction transfer 30 VC")
    void performTransaction_transfer30VC() {
        // when
        MinerTransaction transaction = new MinerTransaction(1, 2, 30);
        transaction.perfomTransaction(miners);

        // then
        assertEquals(70, miners.get(0).getBalance());
        assertEquals(130, miners.get(1).getBalance());
    }
}
