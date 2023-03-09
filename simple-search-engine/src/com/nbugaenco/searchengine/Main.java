package com.nbugaenco.searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> lines = readLines(args);

        byte choice;
        do {
            choice = askUser();
            processChoice(lines, choice);
        } while (choice != 0);
    }

    public static List<String> readLines(String[] args) {
        List<String> lines = new ArrayList<>();

        if (Objects.requireNonNull(args)[0].equals("--data") && args[1] != null) {
            try (Scanner fileScanner = new Scanner(new File(args[1]))) {
                while (fileScanner.hasNextLine()) {
                    lines.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + args[1]);
                System.exit(0);
            }

            return lines;
        }

        throw new IllegalArgumentException("Invalid argument: " + args[0]);
    }

    public static byte askUser() {
        System.out.println(getMenu());
        System.out.print("Enter your choice: ");
        return new Scanner(System.in).nextByte();
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

        StringBuilder result = new StringBuilder((tmp.size() > 0) ? "\nFound people:\n" : "\nNo matching people found.");

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