package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long start = new Date().getTime();

        MiningService miningService = new MiningService();

        Blockchain blockchain = miningService.mineBlocks(new Blockchain(9), 15);

        if (blockchain.isBlockchainValid()) {
            System.out.println("\nBlockchain is valid!");
        } else {
            System.out.println("\nBlockchain is invalid!");
        }

        System.out.println("\nTime to execute: " + TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - start));
    }
}