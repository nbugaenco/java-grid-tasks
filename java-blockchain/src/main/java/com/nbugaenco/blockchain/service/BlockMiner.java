package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.MinerThread;

/**
 * An interface defining the block mining functionality for a blockchain system.
 *
 * @author nbugaenco
 */
public interface BlockMiner {

    /**
     * Mines a block with the given parameters, using the specified mining algorithm.
     *
     * @param givenBlock the block to be mined
     * @param difficulty the difficulty level of mining
     * @param miner      the miner who mines the block
     * @return the mined block
     */
    Block mineBlock(final Block givenBlock, final int difficulty, MinerThread miner);
}
