package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.StringUtils;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Block {

    private final String previousHash;

    private final long timeStamp;

    private final long id;
    private String hash;
    private int nonce = 0;
    private long generationTime;
    private long miner;

    public Block(String previousHash, long id) {
        this.previousHash = previousHash;
        this.id = id;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String calculateHash() {
        return StringUtils.applySha256(this.previousHash + this.id + this.timeStamp + this.nonce);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!this.hash.substring(0, difficulty).equals(target) && !Thread.currentThread().isInterrupted()) {
            this.nonce = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            this.hash = calculateHash();
        }

        generationTime = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - timeStamp);

        miner = StringUtils.getThreadNumber(Thread.currentThread().getName());
    }

    public long getGenerationTime() {
        return generationTime;
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
                "\nBlock was generating for " + this.generationTime + " seconds";
    }
}
