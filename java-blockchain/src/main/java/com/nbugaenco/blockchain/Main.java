package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;
import com.nbugaenco.blockchain.service.implementation.ParallelMiningService;
import com.nbugaenco.blockchain.util.AnsiColors;

public class Main {

    public static void main(String[] args) {
        MiningService miningService = new ParallelMiningService();

        System.out.println(AnsiColors.ITALIC + "Started mining...");
        Blockchain blockchain = miningService.mineBlocks(Blockchain.withDifficulty(0), 15);

        System.out.println(blockchain.isBlockchainValid() ? blockchain : "Invalid blockchain!");
        System.out.println(miningService.getMinersBalance());
    }
}