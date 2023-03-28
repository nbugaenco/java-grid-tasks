package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;
import com.nbugaenco.blockchain.service.implementation.ParallelMiningService;
import com.nbugaenco.blockchain.util.AnsiColors;

public class Main {

    /**
     * The main method demonstrates the mining of blocks in a blockchain
     * using a parallel mining service and displays the resulting blockchain
     * and miners' balances.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a new instance of the ParallelMiningService
        MiningService miningService = new ParallelMiningService();

        // Display the mining start message
        System.out.println(AnsiColors.ITALIC + "Started mining...");

        // Mine 7 blocks with the given mining service
        Blockchain blockchain = miningService.mineBlocks(Blockchain.withDifficulty(0), 7);

        // Check if the blockchain is valid and display the result
        System.out.println(blockchain.isBlockchainValid() ? blockchain : "Invalid blockchain!");

        // Display the miners' balances
        System.out.println(miningService.getMinersBalance());
    }
}