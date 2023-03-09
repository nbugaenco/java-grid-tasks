package com.nbugaenco.searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static final String MENU = """
                                
                === Menu ===
                1. Find a person
                2. Print all people
                0. Exit
                """;

    public static void main(String[] args) {
        List<String> lines = readLines(args);
        Map<String, Set<Integer>> invertedIndexes = invertIndexes(lines);

        byte choice;
        do {
            choice = askUser();
            processChoice(lines, invertedIndexes, choice);
        } while (choice != 0);
    }

    public static Map<String, Set<Integer>> invertIndexes(List<String> lines) {
        Map<String, Set<Integer>> indexes = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] tokens = line.split("\\s+");

            for (String token : tokens) {
                indexes.putIfAbsent(token.toLowerCase(), new HashSet<>());
                indexes.get(token.toLowerCase()).add(i);
            }
        }

        return indexes;
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
        System.out.println(MENU);
        System.out.print("Enter your choice: ");
        return new Scanner(System.in).nextByte();
    }

    public static void processChoice(List<String> lines, Map<String, Set<Integer>> invertedIndexes, byte choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case 1 -> {
                System.out.println("\nEnter a name or email to search all suitable people:");
                String searchQuery = scanner.nextLine();
                System.out.println(findPersons(lines, invertedIndexes, searchQuery));
            }
            case 2 -> {
                System.out.println("\n=== List of people ===");
                lines.forEach(System.out::println);
            }
            case 0 -> System.out.println("\nBye!");
            default -> System.out.println("\nIncorrect option! Try again.");
        }
    }

    public static String findPersons(List<String> lines, Map<String, Set<Integer>> invertedIndexes, String searchQuery) {
        Set<Integer> indexes = invertedIndexes.get(searchQuery.toLowerCase());

        if (indexes == null) {
            return "\nNo matching people found.";
        }

        StringBuilder stringBuilder = new StringBuilder("\n=== Found people ===\n");

        for (Integer index : indexes) {
            stringBuilder.append(lines.get(index)).append("\n");
        }

        return stringBuilder.toString();
    }
}