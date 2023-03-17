package com.nbugaenco.blockchain;

import com.nbugaenco.blockchain.model.Blockchain;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        int difficulty = scanner.nextInt();
        scanner.close();

        Blockchain blockchain = new Blockchain(difficulty);

        blockchain.createBlocks(5);

        if (blockchain.isBlockchainValid()) {
            System.out.println(blockchain);
        } else {
            System.out.println("Blockchain is not valid");
        }
    }
}