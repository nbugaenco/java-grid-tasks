package com.nbugaenco.blockchain.model;

import java.util.concurrent.Callable;

public class Miner implements Callable<Blockchain> {

    private final Blockchain blockchain;

    public Miner(Blockchain blockchain) {
        this.blockchain = new Blockchain(blockchain);
    }

    @Override
    public Blockchain call() {
        blockchain.createBlock();
        return blockchain;
    }
}
