package com.nbugaenco.blockchain.service.implementation;

import com.nbugaenco.blockchain.model.Block;
import com.nbugaenco.blockchain.model.Blockchain;
import com.nbugaenco.blockchain.model.MinerThread;
import com.nbugaenco.blockchain.model.MinerTransaction;
import com.nbugaenco.blockchain.service.MiningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.nbugaenco.blockchain.util.AnsiColors.*;

/**
 * An implementation of the MiningService that mines blocks in parallel threads.
 *
 * @author nbugaenco
 */
public class ParallelMiningService implements MiningService {

    private static final Logger logger = LoggerFactory.getLogger(ParallelMiningService.class);


    private final List<MinerThread> miners;

    /**
     * Constructs a ParallelMiningService with an empty list of miners.
     */
    public ParallelMiningService() {
        this.miners = new ArrayList<>();
    }

    /**
     * Updates the difficulty change information in the last block of the blockchain.
     *
     * @param blockchain    the blockchain containing the block to update
     * @param oldDifficulty the previous difficulty value
     */
    private void makeLastBlockDifficultyChange(final Blockchain blockchain, final int oldDifficulty) {
        Block lastBlock = blockchain.getLastBlock();

        if (oldDifficulty < blockchain.getDifficulty()) {
            lastBlock.setDifficultyChange("N was increased to " + blockchain.getDifficulty());
        } else if (oldDifficulty > blockchain.getDifficulty()) {
            lastBlock.setDifficultyChange("N was decreased to " + blockchain.getDifficulty());
        } else {
            lastBlock.setDifficultyChange("N stays the same");
        }
    }

    /**
     * Fills the list of callable tasks with miners, creating them if necessary.
     *
     * @param callable   the list of callable tasks to fill
     * @param blockchain the blockchain to be mined
     * @return a list of callable tasks filled with miners
     */
    private synchronized List<Callable<Blockchain>> fillCallableTasks(final List<Callable<Blockchain>> callable, final Blockchain blockchain) {
        List<Callable<Blockchain>> tmp = new ArrayList<>(callable);

        if (miners.isEmpty()) {
            for (int j = 0; j < Runtime.getRuntime().availableProcessors(); ++j) {
                miners.add(new MinerThread(new Blockchain(blockchain), 100, j + 1));
            }
        } else {
            miners.forEach(m -> m.setBlockchain(new Blockchain(blockchain)));
        }

        tmp.clear();
        tmp.addAll(miners);

        return tmp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Blockchain mineBlocks(final Blockchain givenChain, final int count) {
        Blockchain blockchain = new Blockchain(givenChain);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<Blockchain>> callableTasks = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            callableTasks = fillCallableTasks(callableTasks, blockchain);

            int oldDifficulty = blockchain.getDifficulty();

            try {
                blockchain = executorService.invokeAny(callableTasks);
                add100ToWinMiner(blockchain);
                performAllTransactionsInBlock(blockchain.getLastBlock());
                makeLastBlockDifficultyChange(blockchain, oldDifficulty);
                logger.info("Thread {} mined block", blockchain.getLastBlock().getMiner());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted\n" + e.getMessage());
            } catch (ExecutionException e) {
                System.err.println("Thread error\n" + e.getMessage());
            }

        }
        executorService.shutdownNow();

        return blockchain;
    }

    /**
     * Performs all transactions in the given block.
     *
     * @param block the block containing the transactions to perform
     */
    private void performAllTransactionsInBlock(Block block) {
        for (MinerTransaction transaction : block.getTransactions()) {
            transaction.perfomTransaction(miners);
        }
    }

    /**
     * Adds 100 virtual coins to the miner who mined the last block in the given blockchain.
     *
     * @param blockchain the blockchain containing the last block
     */
    private void add100ToWinMiner(Blockchain blockchain) {
        int winMiner = blockchain.getLastBlock().getMiner();
        miners.stream()
                .filter(miner -> miner.getId() == winMiner)
                .findFirst().ifPresent(MinerThread::add100);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinersBalance() {
        StringBuilder sb = new StringBuilder();

        for (MinerThread miner : miners) {
            sb
                    .append("\n")
                    .append(BOLD)
                    .append("Miner ")
                    .append(miner.getId())
                    .append(" balance: ")
                    .append(RESET)
                    .append(YELLOW)
                    .append(BOLD)
                    .append(miner.getBalance())
                    .append(" VC")
                    .append(RESET);
        }

        return sb.toString();
    }
}
