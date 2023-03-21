package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.service.BlockMiner;
import com.nbugaenco.blockchain.service.implementation.Sha256BlockMiner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {

    private final List<Block> chain;
    private final BlockMiner blockMiner;
    private int difficulty;

    private Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = Math.abs(difficulty);
        this.blockMiner = new Sha256BlockMiner();
    }

    public Blockchain(Blockchain blockchain) {
        this.chain = new ArrayList<>(blockchain.getChain());
        this.difficulty = Math.abs(blockchain.getDifficulty());
        this.blockMiner = new Sha256BlockMiner();
    }

    public static Blockchain withDifficulty(int difficulty) {
        return new Blockchain(difficulty);
    }

    public synchronized void createBlock() {
        String prevHash = chain.isEmpty() ? "0" : getLastBlock().getHash();

        chain.add(blockMiner.mineBlock(
                Block.create(prevHash, chain.size() + 1L),
                difficulty));

        difficulty = adjustDifficulty();
    }

    public synchronized List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }

    public int getDifficulty() {
        return difficulty;
    }

    private synchronized int adjustDifficulty() {
        if (this.getLastBlock().getGenerationTime() > 60) {
            return this.difficulty - 1;
        } else if (this.getLastBlock().getGenerationTime() < 15) {
            return this.difficulty + 1;
        }
        return this.difficulty;
    }

    public synchronized boolean isBlockchainValid() {
        for (Block block : chain) {
            if (!isBlockValid(block)) return false;
        }

        return true;
    }

    public boolean isBlockValid(Block block) {
        return checkOwnHash(block) &&
                checkPreviousHash(block);
    }

    private boolean checkPreviousHash(Block block) {
        Block prevBlock = getPreviousBlock(block);

        if (prevBlock == null) {
            return block.getPreviousHash().equals("0");
        } else {
            return block.getPreviousHash().equals(prevBlock.getHash());
        }
    }

    private boolean checkOwnHash(Block block) {
        return block.getHash().equals(block.calculateHash());
    }

    private Block getPreviousBlock(Block block) {
        int index = chain.indexOf(block);

        return (index == 0) ? null : chain.get(index - 1);
    }

    public synchronized Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}