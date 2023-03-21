package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;
import com.nbugaenco.blockchain.service.implementation.ParallelMiningService;

public class Main {

    public static void main(String[] args) {
        MiningService miningService = new ParallelMiningService();
        Blockchain blockchain = miningService.mineBlocks(Blockchain.withDifficulty(0), 7);

        System.out.println(blockchain);
    }
}