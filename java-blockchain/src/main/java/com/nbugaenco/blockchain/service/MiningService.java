package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Blockchain;

public interface MiningService {
    Blockchain mineBlocks(final Blockchain givenChain, final int n);

    String getMinersBalance();
}
