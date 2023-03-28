package com.nbugaenco.blockchain.model;

import java.util.concurrent.Callable;

/**
 * Represents a miner thread in the blockchain system. This class implements Callable, allowing it to be
 * executed by a thread executor, creating a new block in the associated blockchain when called.
 * Each miner thread has an ID, balance, and associated blockchain.
 *
 * @author nbugaenco
 */
public class MinerThread implements Callable<Blockchain> {

    private final Integer id;
    private Blockchain blockchain;
    private int balance;

    /**
     * Constructs a new MinerThread instance with the specified blockchain, balance, and unique ID.
     *
     * @param blockchain the Blockchain instance associated with this miner thread
     * @param balance    the initial balance of the miner thread
     * @param id         the unique ID of the miner thread
     */
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

    /**
     * Increases the miner's balance by 100.
     */
    public void add100() {
        this.balance += 100;
    }

    /**
     * Subtracts the specified amount from the miner's balance.
     *
     * @param amount the amount to subtract from the miner's balance
     */
    public void subtractBalance(int amount) {
        this.balance -= amount;
    }

    /**
     * Adds the specified amount to the miner's balance.
     *
     * @param amount the amount to add to the miner's balance
     */
    public void addBalance(int amount) {
        this.balance += amount;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Creates a new block in the associated blockchain and returns the updated blockchain.
     * This method is called when the miner thread is executed by a thread executor.
     *
     * @return the updated blockchain with the new block created by this miner thread
     */
    @Override
    public Blockchain call() {
        blockchain.createBlock(this);
        return blockchain;
    }
}
