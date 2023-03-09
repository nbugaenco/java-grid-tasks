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

        System.out.print("\nEnter the number of search queries: ");
        int searchCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < searchCount; ++i) {
            System.out.print("\nEnter data to search people: ");
            String searchQuery = scanner.nextLine();

            List<String> tmp = lines.stream()
                    .filter(l -> l.toLowerCase().contains(searchQuery.toLowerCase()))
                    .toList();

            System.out.println(
                    (tmp.size() > 0) ? "\nFound people:" : "\nNo matching people found."
            );

            tmp.forEach(System.out::println);
        }

    }
}