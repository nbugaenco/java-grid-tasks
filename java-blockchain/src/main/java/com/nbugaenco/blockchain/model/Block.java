package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.HashUtils;

import java.util.Date;

public class Block {

    private final String hash;

    private final String previousHash;

    private final long timeStamp;

    private final long id;

    public Block(String previousHash, long id) {
        this.previousHash = previousHash;
        this.id = id;
        this.timeStamp = new Date().getTime();
        this.hash = calculateBlockHash();
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String calculateBlockHash() {
        return HashUtils.applySha256(this.previousHash + this.id + this.timeStamp);
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Id: " + this.id +
                "\nTimestamp: " + this.timeStamp +
                "\nHash of the previous block:\n" + this.previousHash +
                "\nHash of the block:\n" + this.hash;
    }
}
