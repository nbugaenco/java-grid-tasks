package com.nbugaenco.blockchain.service.implementation;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.model.MinerThread;
import com.nbugaenco.blockchain.service.MiningService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelMiningService implements MiningService {

    private static void makeLastBlockDifficultyChange(final Blockchain blockchain, final int oldDifficulty) {
        if (blockchain.isBlockchainValid()) {
            Block lastBlock = blockchain.getLastBlock();

            if (oldDifficulty < blockchain.getDifficulty()) {
                lastBlock.setDifficultyChange("N was increased to " + blockchain.getDifficulty());
            } else if (oldDifficulty > blockchain.getDifficulty()) {
                lastBlock.setDifficultyChange("N was decreased to " + blockchain.getDifficulty());
            } else {
                lastBlock.setDifficultyChange("N stays the same");
            }
        }
    }

    private static List<Callable<Blockchain>> fillCallableTasks(final List<Callable<Blockchain>> callable, final Blockchain blockchain) {
        List<Callable<Blockchain>> tmp = new ArrayList<>(callable);

        if (tmp.isEmpty()) {
            for (int j = 0; j < Runtime.getRuntime().availableProcessors(); ++j) {
                tmp.add(new MinerThread(blockchain));
            }
        } else {
            tmp.replaceAll(ignored -> new MinerThread(blockchain));
        }

        return tmp;
    }

    @Override
    public Blockchain mineBlocks(final Blockchain givenChain, final int n) {
        Blockchain blockchain = new Blockchain(givenChain);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<Blockchain>> callableTasks = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            callableTasks = fillCallableTasks(callableTasks, blockchain);

            int oldDifficulty = blockchain.getDifficulty();

            try {
                blockchain = executorService.invokeAny(callableTasks);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            makeLastBlockDifficultyChange(blockchain, oldDifficulty);
        }
        executorService.shutdownNow();

        return blockchain;
    }
}
