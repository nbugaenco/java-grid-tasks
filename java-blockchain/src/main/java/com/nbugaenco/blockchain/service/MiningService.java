package com.nbugaenco.blockchain.service;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.model.Miner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiningService {

    public Blockchain mineBlocks(Blockchain givenChain, int n) {
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
        executorService.shutdownNow();

        return blockchain;
    }

    private List<Callable<Blockchain>> fillCallableTasks(List<Callable<Blockchain>> callable, Blockchain blockchain) {
        List<Callable<Blockchain>> tmp = new ArrayList<>(callable);

        if (tmp.isEmpty()) {
            for (int j = 0; j < Runtime.getRuntime().availableProcessors(); ++j) {
                tmp.add(new Miner(blockchain));
            }
        } else {
            tmp.replaceAll(ignored -> new Miner(blockchain));
        }

        return tmp;
    }
}
