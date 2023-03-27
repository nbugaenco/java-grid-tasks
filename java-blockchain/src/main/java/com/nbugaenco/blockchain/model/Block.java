package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.StringUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.nbugaenco.blockchain.util.AnsiColors.*;

public class Block {

    private final String previousHash;
    private final long timeStamp;
    private final long id;
    private final List<MinerTransaction> transactions = new ArrayList<>();
    private String hash;
    private int nonce = 0;
    private long generationTime;
    private int miner;
    private String difficultyChange;

    public Block(Block block) {
        this.previousHash = block.previousHash;
        this.id = block.id;
        this.timeStamp = block.timeStamp;
        this.hash = block.hash;
        this.nonce = block.nonce;
        this.generationTime = block.generationTime;
        this.miner = block.miner;
        this.difficultyChange = block.difficultyChange;
        this.transactions.addAll(block.transactions);
    }

    private Block(String previousHash, long id) {
        this.previousHash = previousHash;
        this.id = id;
        this.timeStamp = Instant.now().getEpochSecond();
        this.hash = calculateHash();
    }

    public static Block create(String previousHash, long id) {
        return new Block(previousHash, id);
    }

    public String calculateHash() {
        return StringUtil.applySha256(this.previousHash + this.id + this.timeStamp + this.nonce);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public void setDifficultyChange(String difficultyChange) {
        this.difficultyChange = difficultyChange;
    }

    public long calculateGenerationTime() {
        return Instant.now().getEpochSecond() - timeStamp;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(long generationTime) {
        this.generationTime = generationTime;
    }

    public List<MinerTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public int getMiner() {
        return miner;
    }

    public void setMiner(int miner) {
        this.miner = miner;
    }

    public void addTransaction(MinerTransaction transaction) {
        transactions.add(transaction);
    }

    private String getBlockMessages() {
        if (transactions.isEmpty()) {
            return "no transactions";
        }
        return transactions.stream().map(MinerTransaction::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return BOLD +
                "\nBlock:" +
                RESET +
                UNDERLINE +
                "\nCreated by miner # " + this.miner +
                RESET +
                "\nMiner " + this.miner + " gets "
                + YELLOW + BOLD +
                "100 VC" +
                RESET + BLUE + ITALIC +
                "\nId: " +
                RESET +
                this.id +
                BLUE + ITALIC +
                "\nTimestamp: " +
                RESET +
                this.timeStamp +
                MAGENTA + ITALIC +
                "\nMagic number: " +
                RESET +
                this.nonce +
                RED + BOLD + ITALIC +
                "\nHash of the previous block:\n" +
                RESET +
                this.previousHash +
                GREEN + BOLD + ITALIC +
                "\nHash of the block:\n" +
                RESET +
                this.hash +
                BOLD + UNDERLINE +
                "\nBlock data:\n" +
                RESET +
                getBlockMessages() +
                CYAN + ITALIC +
                "\nBlock was generating for " +
                RESET + BOLD +
                this.generationTime +
                RESET + CYAN +
                " seconds" +
                RESET + ITALIC +
                "\n" + this.difficultyChange +
                RESET;
    }
}
