package com.nbugaenco.blockchain.model;

import java.util.List;

import static com.nbugaenco.blockchain.util.AnsiColors.*;

public class MinerTransaction {

    private final int from;
    private final int to;
    private final int amount;

    public MinerTransaction(int from, int to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

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

    @Override
    public String toString() {
        return "Miner " + from + " sent " + YELLOW + BOLD + amount + " VC " + RESET + "to Miner " + to;
    }
}
