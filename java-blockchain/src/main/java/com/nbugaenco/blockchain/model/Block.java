package com.nbugaenco.blockchain.model;

import com.nbugaenco.blockchain.util.StringUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.nbugaenco.blockchain.util.AnsiColors.*;

/**
 * Represents a block in the blockchain, containing a list of transactions, a previous block's hash,
 * a timestamp, a unique ID, and other properties related to mining. Provides methods for
 * calculating the block's hash, adding transactions, and generating a string representation of the block.
 *
 * @author nbugaenco
 */
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

    /**
     * Constructs a new Block instance by copying the contents and properties of another Block instance.
     *
     * @param block the Block instance to copy from
     */
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

    /**
     * Constructs a new Block instance with the specified previous hash and unique ID.
     * The constructor is private to enforce the use of the {@link #create(String, long)} factory method.
     *
     * @param previousHash the hash of the previous block
     * @param id           the unique ID of the block
     */
    private Block(String previousHash, long id) {
        this.previousHash = previousHash;
        this.id = id;
        this.timeStamp = Instant.now().getEpochSecond();
        this.hash = calculateHash();
    }

    /**
     * Creates a new Block instance with the specified previous hash and unique ID.
     *
     * @param previousHash the hash of the previous block
     * @param id           the unique ID of the block
     * @return a new Block instance with the specified previous hash and unique ID
     */
    public static Block create(String previousHash, long id) {
        return new Block(previousHash, id);
    }

    /**
     * Calculates the hash of the block using its content.
     *
     * @return the calculated hash of the block
     */
    public String calculateHash() {
        return StringUtil.applySha256(this.previousHash + this.id + this.timeStamp + this.nonce);
    }

    public void calculateAndUpdateHash() {
        this.hash = calculateHash();
    }

    public String getHash() {
        return hash;
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

    /**
     * Adds a MinerTransaction to the block.
     *
     * @param transaction the {@link MinerTransaction} to add
     */
    public void addTransaction(MinerTransaction transaction) {
        transactions.add(transaction);
    }

    private String getBlockMessages() {
        if (transactions.isEmpty()) {
            return "no transactions";
        }
        return transactions.stream().map(MinerTransaction::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Returns a string representation of the block, showing its properties and the contents of its transactions.
     *
     * @return a string representation of the block
     */
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
