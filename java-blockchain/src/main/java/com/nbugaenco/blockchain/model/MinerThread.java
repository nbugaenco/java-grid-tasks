package com.nbugaenco.blockchain.model;

import java.util.concurrent.Callable;

public class MinerThread implements Callable<Blockchain> {

    private final Blockchain blockchain;

    public MinerThread(Blockchain blockchain) {
        this.blockchain = new Blockchain(blockchain);
    }

    @Override
    public Blockchain call() {
        blockchain.createBlock();
        return blockchain;
    }
}
