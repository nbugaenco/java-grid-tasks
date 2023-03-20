package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;

public class Main {

    public static void main(String[] args) {
        MiningService miningService = new MiningService();
        Blockchain blockchain = miningService.mineBlocks(Blockchain.withDifficulty(0), 8);

        System.out.println(blockchain);
    }
}