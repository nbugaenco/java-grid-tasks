package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.exception.InvalidBlockException;
import com.nbugaenco.blockchain.service.BlockMiner;
import com.nbugaenco.blockchain.service.implementation.Sha256BlockMiner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a Blockchain, which is a continuous sequence of blocks connected through
 * cryptographic hashes. Each block contains a previous block's hash, forming a chain of blocks.
 * The class provides methods for creating blocks, validating the entire blockchain,
 * and adjusting the mining difficulty based on block generation time.
 *
 * @author nbugaenco
 */
public class Blockchain {

    private static final int UPPER_TIME_LIMIT = 60;
    private static final int LOWER_TIME_LIMIT = 15;

    private final List<Block> chain;
    private final BlockMiner blockMiner;
    private int difficulty;

    /**
     * Constructs a new Blockchain instance with the specified mining difficulty.
     * The constructor is private to enforce the use of the {@link #withDifficulty(int)} factory method.
     *
     * @param difficulty the mining difficulty
     */
    private Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = Math.abs(difficulty);
        this.blockMiner = new Sha256BlockMiner();
    }

    /**
     * Constructs a new Blockchain instance by copying the contents and properties of another Blockchain instance.
     *
     * @param blockchain the Blockchain instance to copy from
     */
    public Blockchain(Blockchain blockchain) {
        this.chain = new ArrayList<>(blockchain.getChain());
        this.difficulty = Math.abs(blockchain.getDifficulty());
        this.blockMiner = blockchain.blockMiner;
    }

    /**
     * Creates a new Blockchain instance with the specified mining difficulty.
     *
     * @param difficulty the mining difficulty
     * @return a new Blockchain instance with the specified mining difficulty
     */
    public static Blockchain withDifficulty(int difficulty) {
        return new Blockchain(difficulty);
    }

    /**
     * Creates and adds a new block to the blockchain by mining it using the provided MinerThread instance.
     *
     * @param miner the MinerThread instance to mine the block
     */
    public synchronized void createBlock(MinerThread miner) {
        String prevHash = chain.isEmpty() ? "0" : getLastBlock().getHash();

        chain.add(blockMiner.mineBlock(
                Block.create(prevHash, chain.size() + 1L),
                difficulty, miner));

        difficulty = adjustDifficulty();
    }

    /**
     * Returns an unmodifiable list of the current chain of blocks.
     *
     * @return an unmodifiable list of blocks in the blockchain
     */
    public synchronized List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }

    /**
     * Returns the current mining difficulty.
     *
     * @return the current mining difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    private synchronized int adjustDifficulty() {
        if (this.getLastBlock().getGenerationTime() > UPPER_TIME_LIMIT) {
            return this.difficulty == 0 ? 0 : this.difficulty - 1;
        } else if (this.getLastBlock().getGenerationTime() < LOWER_TIME_LIMIT) {
            return this.difficulty + 1;
        }
        return this.difficulty;
    }

    /**
     * Validates the entire blockchain, checking the consistency of each block's hash and
     * its connection to the previous block's hash.
     *
     * @return true if the blockchain is valid, false otherwise
     */
    public synchronized boolean isBlockchainValid() {
        for (Block block : chain) {
            if (!isBlockValid(block)) return false;
        }

        return true;
    }

    /**
     * Validates a single block by checking its hash and its connection to the previous block's hash.
     *
     * @param block the block to validate
     * @return true if the block is valid, false otherwise
     * @throws InvalidBlockException if the block is invalid
     */
    public boolean isBlockValid(Block block) {
        if (!checkOwnHash(block)) {
            throw new InvalidBlockException(String.format(
                    "%nInvalid block hash: %s.%nShould be: %s",
                    block.getHash(),
                    block.calculateHash()
            ));
        } else if (!checkPreviousHash(block)) {
            throw new InvalidBlockException(String.format(
                    "%nInvalid previous block hash: %s.%nShould be: %s",
                    block.getPreviousHash(),
                    Optional.ofNullable(getPreviousBlock(block)).orElse(Block.create("0", -1)).getHash()
            ));
        }

        return true;
    }

    /**
     * Checks if the previous block's hash in the given block is valid by comparing it with the
     * actual previous block's hash in the blockchain.
     *
     * @param block the block to check the previous hash for
     * @return true if the previous hash is valid, false otherwise
     */
    private boolean checkPreviousHash(Block block) {
        Block prevBlock = getPreviousBlock(block);

        if (prevBlock == null) {
            return block.getPreviousHash().equals("0");
        } else {
            return block.getPreviousHash().equals(prevBlock.getHash());
        }
    }

    /**
     * Checks if the given block's hash is valid by comparing it with the hash
     * calculated using the block's content.
     *
     * @param block the block to check the hash for
     * @return true if the block's hash is valid, false otherwise
     */
    private boolean checkOwnHash(Block block) {
        return block.getHash().equals(block.calculateHash());
    }

    /**
     * Retrieves the previous block in the blockchain for the given block.
     * If the given block is the first block in the chain, returns null.
     *
     * @param block the block to find the previous block for
     * @return the previous block in the blockchain, or null if the given block is the first block
     */
    private Block getPreviousBlock(Block block) {
        int index = chain.indexOf(block);

        return (index == 0) ? null : chain.get(index - 1);
    }

    /**
     * Returns the last block in the blockchain.
     *
     * @return the last block in the blockchain
     */
    public synchronized Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    /**
     * Returns a string representation of the blockchain, showing the contents of each block.
     *
     * @return a string representation of the blockchain
     */
    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}