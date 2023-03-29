package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.service.MiningService;
import com.nbugaenco.blockchain.service.implementation.ParallelMiningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
        logger.info("Starting mining...\n");

        // Mine 7 blocks with the given mining service
        Blockchain blockchain = miningService.mineBlocks(Blockchain.withDifficulty(0), 10);

        // Check if the blockchain is valid and display the result
        System.out.println(blockchain.isBlockchainValid() ? blockchain : "Invalid blockchain!");

        // Display the miners' balances
        System.out.println(miningService.getMinersBalance());
    }
}