package com.nbugaenco.searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of people: ");
        int linesCount = scanner.nextInt();
        scanner.nextLine();

        List<String> lines = new ArrayList<>(linesCount);

        System.out.println("Enter all people:");
        for (int i = 0; i < linesCount; ++i) {
            lines.add(scanner.nextLine());
        }

        byte choice;
        do {
            System.out.println(getMenu());
            System.out.print("Enter your choice: ");
            choice = scanner.nextByte();
            scanner.nextLine();

            processChoice(lines, choice);
        } while (choice != 0);
    }

    public static void processChoice(List<String> lines, byte choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case 1 -> {
                System.out.println("\nEnter a name or email to search all suitable people.");
                String searchQuery = scanner.nextLine();
                System.out.println(findAPerson(lines, searchQuery));
            }
            case 2 -> {
                System.out.println("\n=== List of people ===");
                lines.forEach(System.out::println);
            }
            case 0 -> System.out.println("\nBye!");
            default -> System.out.println("\nIncorrect option! Try again.");
        }
    }

    public static String findAPerson(List<String> lines, String searchQuery) {
        List<String> tmp = lines.stream()
                .filter(l -> l.toLowerCase().contains(searchQuery.toLowerCase()))
                .toList();

        StringBuilder result = new StringBuilder((tmp.size() > 0) ? "\n=== Found people ===\n" : "\nNo matching people found.");

        tmp.forEach(s -> result.append(s).append("\n"));

        return result.toString();
    }

    public static String getMenu() {
        return """
                
                === Menu ===
                1. Find a person
                2. Print all people
                0. Exit
                """;
    }
}