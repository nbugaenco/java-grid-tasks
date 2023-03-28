package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Blockchain;

/**
 * Represents a service responsible for mining blocks in a blockchain.
 *
 * @author nbugaenco
 */
public interface MiningService {

    /**
     * Mines a given number of blocks and adds them to the given blockchain.
     *
     * @param givenChain the blockchain to mine blocks on
     * @param count      the number of blocks to mine
     * @return the updated blockchain after mining the specified number of blocks
     */
    Blockchain mineBlocks(final Blockchain givenChain, final int count);

    /**
     * Retrieves the balance of all miners.
     *
     * @return a string representation of the miners' balances
     */
    String getMinersBalance();
}
