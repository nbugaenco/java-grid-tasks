package com.nbugaenco.blockchain.service.implementation;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.MinerThread;
import com.nbugaenco.blockchain.model.MinerTransaction;
import com.nbugaenco.blockchain.service.BlockMiner;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of the BlockMiner interface using the SHA-256 hashing algorithm.
 * This class is responsible for mining blocks, creating random transactions, and maintaining a
 * transaction queue for the blockchain system.
 *
 * @author com.nbugaenco.blockchain.service.implementation
 */
public class Sha256BlockMiner implements BlockMiner {

    private static final ConcurrentLinkedQueue<MinerTransaction> threadTransactions = new ConcurrentLinkedQueue<>();

    /**
     * Checks if the block's hash satisfies the given difficulty by comparing the hash prefix
     * with the target string.
     *
     * @param difficulty the mining difficulty
     * @param block      the block to be checked
     * @return true if the block's hash satisfies the difficulty, false otherwise
     */
    private static boolean satisfyDifficulty(int difficulty, Block block) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return block.getHash().substring(0, difficulty).equals(target);
    }

    /**
     * Creates a random transaction from the given miner to another miner.
     *
     * @param miner the miner who sends the transaction
     * @return a randomly generated MinerTransaction
     */
    private static MinerTransaction createRandomTransaction(MinerThread miner) {
        int minersCount = Runtime.getRuntime().availableProcessors();
        int toMiner = miner.getId();

        while (toMiner == miner.getId()) {
            toMiner = ThreadLocalRandom.current().nextInt(1, minersCount + 1);
        }

        int preBalance = preCheckBalance(miner);
        int amount = preBalance <= 1 ? 1 : ThreadLocalRandom.current().nextInt(1, preBalance);

        return new MinerTransaction(
                miner.getId(),
                toMiner,
                amount
        );
    }

    /**
     * Calculates the maximum available balance for the given miner, considering the
     * transactions in the threadTransactions queue.
     *
     * @param miner the miner whose balance is being checked
     * @return the maximum available balance
     */
    private static int preCheckBalance(MinerThread miner) {
        int maxAvailBalance = miner.getBalance();
        List<MinerTransaction> transactionList = threadTransactions.stream()
                .filter(tran -> tran.getFrom() == miner.getId())
                .toList();

        for (MinerTransaction tr : transactionList) {
            maxAvailBalance -= tr.getAmount();
        }

        return maxAvailBalance;
    }

    /**
     * Mines a block using the SHA-256 hashing algorithm, satisfying the given difficulty.
     * Random transactions are created and added to the block during the mining process.
     *
     * @param givenBlock the block to be mined
     * @param difficulty the difficulty level of mining
     * @param miner      the miner who mines the block
     * @return the mined block
     */
    @Override
    public Block mineBlock(final Block givenBlock, final int difficulty, MinerThread miner) {
        Block block = new Block(givenBlock);

        while (!satisfyDifficulty(difficulty, block) &&
                !Thread.currentThread().isInterrupted()) {
            int nonce = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            block.setNonce(nonce);
            block.setHash(block.calculateHash());
            tryCreateTransaction(miner);
        }

        block.setGenerationTime(block.calculateGenerationTime());
        block.setMiner(miner.getId());

        synchronized (threadTransactions) {
            while (!threadTransactions.isEmpty()) {
                if (chance10Percent()) {
                    threadTransactions.clear();
                    break;
                }
                block.addTransaction(threadTransactions.poll());
            }
        }

        return block;
    }

    private void tryCreateTransaction(MinerThread miner) {
        if (preCheckBalance(miner) > 0 && threadTransactions.size() < 5 && chance10Percent()) {
            threadTransactions.add(createRandomTransaction(miner));
        }
    }

    /**
     * Returns true with a 10% probability, false otherwise.
     *
     * @return true if a random number is within the 10% range, false otherwise
     */
    private synchronized boolean chance10Percent() {
        return ThreadLocalRandom.current().nextDouble() < 0.1;
    }
}
