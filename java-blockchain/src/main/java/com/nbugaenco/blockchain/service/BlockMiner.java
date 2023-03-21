package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Block;

public interface BlockMiner {
    Block mineBlock(final Block givenBlock, final int difficulty);
}
