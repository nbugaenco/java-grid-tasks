package com.nbugaenco.blockchain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {
    private final List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
    }

    public void createBlock() {
        String prevHash = chain.isEmpty() ? "0" : chain.get(chain.size() - 1).getHash();
        Block newBlock = new Block(prevHash, chain.size() + 1L);
        chain.add(newBlock);
    }

    public boolean verifyBlockchain() {
        boolean flag = true;
        for (int i = 0; i < chain.size(); i++) {
            String previousHash = i == 0 ? "0" : chain.get(i - 1).getHash();
            flag = chain.get(i).getHash().equals(chain.get(i).calculateBlockHash())
                    && previousHash.equals(chain.get(i).getPreviousHash());
            if (!flag) break;
        }

        return flag;
    }

    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n\n"));
    }
}