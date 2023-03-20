package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class BlockMiner {

    public void mineBlock(Block block, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!block.getHash().substring(0, difficulty).equals(target) && !Thread.currentThread().isInterrupted()) {
            int nonce = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            block.setNonce(nonce);
            block.setHash(block.calculateHash());
        }

        block.setGenerationTime(block.calculateGenerationTime());
        block.setMiner(StringUtils.getThreadNumber(Thread.currentThread().getName()));
    }
}
