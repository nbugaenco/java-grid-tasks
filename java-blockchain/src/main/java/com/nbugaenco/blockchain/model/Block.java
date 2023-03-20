package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.StringUtils;

import java.time.Instant;

public class Block {

    private final String previousHash;
    private final long timeStamp;
    private final long id;
    private String hash;
    private int nonce = 0;
    private long generationTime;
    private long miner;
    private String difficultyChange;

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
        return StringUtils.applySha256(this.previousHash + this.id + this.timeStamp + this.nonce);
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

    public void setMiner(long miner) {
        this.miner = miner;
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

    @Override
    public String toString() {
        return "\nBlock:" +
                "\nCreated by miner # " + this.miner +
                "\nId: " + this.id +
                "\nTimestamp: " + this.timeStamp +
                "\nMagic number: " + this.nonce +
                "\nHash of the previous block:\n" + this.previousHash +
                "\nHash of the block:\n" + this.hash +
                "\nBlock was generating for " + this.generationTime + " seconds" +
                "\n" + this.difficultyChange;
    }
}
