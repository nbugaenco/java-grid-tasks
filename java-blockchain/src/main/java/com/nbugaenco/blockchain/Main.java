package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();

        for (int i = 1; i <= 5; ++i) {
            blockchain.createBlock();
        }

        if (blockchain.verifyBlockchain()) {
            System.out.println(blockchain);
        } else {
            System.out.println("Blockchain is not valid");
        }
    }
}