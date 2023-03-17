package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.HashUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Block {

    private final String previousHash;

    private final long timeStamp;

    private final long id;

    private String hash;

    private int nonce = 0;

    private final Random random;

    private long generationTime;

    public Block(String previousHash, long id) {
        this.previousHash = previousHash;
        this.id = id;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();

        try {
            this.random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String calculateHash() {
        return HashUtils.applySha256(this.previousHash + this.id + this.timeStamp + this.nonce);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!this.hash.substring(0, difficulty).equals(target)) {
            this.nonce = random.nextInt(Integer.MAX_VALUE);
            this.hash = calculateHash();
        }

        generationTime = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - timeStamp);
    }

    @Override
    public String toString() {
        return "\nBlock:\n" +
                "Id: " + this.id +
                "\nTimestamp: " + this.timeStamp +
                "\nMagic number: " + this.nonce +
                "\nHash of the previous block:\n" + this.previousHash +
                "\nHash of the block:\n" + this.hash +
                "\nBlock was generating for " + this.generationTime + " seconds";
    }
}
