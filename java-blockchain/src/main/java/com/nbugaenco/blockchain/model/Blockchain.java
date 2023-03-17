package com.nbugaenco.blockchain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {

    private final List<Block> chain;

    private final int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = Math.abs(difficulty);
    }

    public void createBlock() {
        String prevHash = chain.isEmpty() ? "0" : getLastBlock().getHash();
        Block newBlock = new Block(prevHash, chain.size() + 1L);
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public void createBlocks(int count) {
        for (int i = 0; i < count; i++) {
            createBlock();
        }
    }

    public boolean isBlockchainValid() {
        for (Block block : chain) {
            if (!isBlockValid(block)) return false;
        }

        return true;
    }

    private boolean isBlockValid(Block block) {
        return checkDifficulty(block) &&
                checkOwnHash(block) &&
                checkPreviousHash(block);
    }

    private boolean checkPreviousHash(Block block) {
        if (getPreviousBlock(block) == null) {
            return block.getPreviousHash().equals("0");
        }

        return block.getPreviousHash().equals(getPreviousBlock(block).getHash());
    }

    private boolean checkOwnHash(Block block) {
        return block.getHash().equals(block.calculateHash());
    }

    private boolean checkDifficulty(Block block) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return block.getHash().substring(0, difficulty).equals(target);
    }

    private Block getPreviousBlock(Block block) {
        int index = chain.indexOf(block);

        return (index == 0) ? null : chain.get(index - 1);
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}