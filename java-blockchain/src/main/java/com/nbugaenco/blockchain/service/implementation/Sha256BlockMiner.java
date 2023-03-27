package com.nbugaenco.blockchain.service.implementation;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.MinerThread;
import com.nbugaenco.blockchain.model.MinerTransaction;
import com.nbugaenco.blockchain.service.BlockMiner;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Sha256BlockMiner implements BlockMiner {

    private static final ConcurrentLinkedQueue<MinerTransaction> threadTransactions = new ConcurrentLinkedQueue<>();

    private static boolean satisfyDifficulty(int difficulty, Block block) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return block.getHash().substring(0, difficulty).equals(target);
    }

    private static MinerTransaction createRandomTransaction(MinerThread miner) {
        int minersCount = Runtime.getRuntime().availableProcessors();
        int toMiner = miner.getId();

        while (toMiner == miner.getId()) {
            toMiner = ThreadLocalRandom.current().nextInt(1, minersCount + 1);
        }

        int amount = ThreadLocalRandom.current().nextInt(1, preCheckBalance(miner));
        return new MinerTransaction(
                miner.getId(),
                toMiner,
                amount
        );
    }

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

    @Override
    public Block mineBlock(final Block givenBlock, final int difficulty, MinerThread miner) {
        Block block = new Block(givenBlock);

        if (Thread.currentThread().isInterrupted()) return null;

        while (!satisfyDifficulty(difficulty, block) &&
                !Thread.currentThread().isInterrupted()) {
            int nonce = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            block.setNonce(nonce);
            block.setHash(block.calculateHash());

            synchronized (threadTransactions) {
                if (chance10Percent() && threadTransactions.size() < 5 && preCheckBalance(miner) != 0) {
                    threadTransactions.add(createRandomTransaction(miner));
                }
            }
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

    private synchronized boolean chance10Percent() {
        return ThreadLocalRandom.current().nextDouble() < 0.1;
    }
}
