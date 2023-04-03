package com.nbugaenco.blockchain.model;

import java.util.List;

import static com.nbugaenco.blockchain.util.AnsiColors.*;

/**
 * Represents a transaction between two miners in the blockchain system.
 * A transaction contains the sender, receiver, and the amount to be transferred.
 *
 * @author com.nbugaenco.blockchain
 */
public class MinerTransaction {

    private final int from;
    private final int to;
    private final int amount;

    /**
     * Constructs a new MinerTransaction instance with the specified sender, receiver, and amount.
     *
     * @param from   the ID of the sender miner
     * @param to     the ID of the receiver miner
     * @param amount the amount to be transferred in the transaction
     */
    public MinerTransaction(int from, int to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    /**
     * Performs the transaction, updating the balances of the sender and receiver miners.
     *
     * @param miners a list of {@link MinerThread} instances representing all miners in the system
     * @throws IndexOutOfBoundsException if either the sender or receiver miner is not found in the list
     */
    public void perfomTransaction(List<MinerThread> miners) {
        MinerThread fromMiner = miners.stream()
                .filter(m -> m.getId() == from)
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);

        MinerThread toMiner = miners.stream()
                .filter(m -> m.getId() == to)
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);

        fromMiner.subtractBalance(amount);
        toMiner.addBalance(amount);
    }

    public int getFrom() {
        return from;
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Returns a string representation of the transaction, including sender, receiver, and amount.
     *
     * @return a string representation of the transaction
     */
    @Override
    public String toString() {
        return "Miner " + from + " sent " + YELLOW + BOLD + amount + " VC " + RESET + "to Miner " + to;
    }
}
