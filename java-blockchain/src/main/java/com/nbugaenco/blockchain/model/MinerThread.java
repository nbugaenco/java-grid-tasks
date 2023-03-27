package com.nbugaenco.blockchain.model;

import java.util.concurrent.Callable;

public class MinerThread implements Callable<Blockchain> {

    private final Integer id;
    private Blockchain blockchain;
    private int balance;

    public MinerThread(Blockchain blockchain, int balance, Integer id) {
        this.blockchain = blockchain;
        this.balance = balance;
        this.id = id;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public int getBalance() {
        return balance;
    }

    public void add100() {
        this.balance += 100;
    }

    public void subtractBalance(int amount) {
        this.balance -= amount;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Blockchain call() {
        blockchain.createBlock(this);
        return blockchain;
    }
}
