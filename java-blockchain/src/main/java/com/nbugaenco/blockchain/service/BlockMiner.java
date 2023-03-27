package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.MinerThread;

public interface BlockMiner {
    Block mineBlock(final Block givenBlock, final int difficulty, MinerThread miner);
}
