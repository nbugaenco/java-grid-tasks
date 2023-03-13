package searchengine.util;

import searchengine.model.SearchDataset;
import searchengine.model.SearchEngineContext;
import searchengine.model.SearchStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Utility class for Search Engine
 */
public class SearchEngineUtils {

    public static final String MENU = """
                            
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
            """;

    private SearchEngineUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Reading data from file given in CLI arguments
     *
     * @param args CLI arguments
     * @return {@link List} of strings read from file
     */
    public static List<String> readFile(String[] args) {
        List<String> lines = new ArrayList<>();

        if (Objects.requireNonNull(args)[0].equals("--data") && args[1] != null) {
            try (Scanner fileScanner = new Scanner(new File(args[1]))) {
                while (fileScanner.hasNextLine()) {
                    lines.add(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + args[1]);
                System.exit(1);
            }

            return lines;
        }

        throw new IllegalArgumentException("Invalid argument: " + args[0]);
    }

    /**
     * Prints menu and asks user for a choice
     *
     * @return user's choice
     */
    public static byte askUser() {
        System.out.println(MENU);
        System.out.print("Enter your choice: ");
        return new Scanner(System.in).nextByte();
    }

    /**
     * Making something, that user has chosen in the menu
     *
     * @param context {@link SearchEngineContext} that contains needed data
     * @param choice  Menu item that user has chosen
     */
    public static void processMenuItem(SearchEngineContext context, byte choice) {
        switch (choice) {
            case 1 -> findPerson(context);
            case 2 -> {
                System.out.println("\n=== List of people ===");
                context.getDataset().getLines().forEach(System.out::println);
            }
            case 0 -> System.out.println("\nBye!");
            default -> System.out.println("\nIncorrect option! Try again.");
        }
    }

    /**
     * Method for searching some data from given {@link SearchDataset} (is the 1st menu item)
     *
     * @param context {@link SearchDataset} to search in
     */
    private static void findPerson(SearchEngineContext context) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect a matching strategy (ALL, ANY, NONE): ");
        SearchStrategy searchStrategy = SearchStrategy.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("\nEnter a name or email to search all suitable people:");
        String searchQuery = scanner.nextLine();

        context.setSearchMethod(searchStrategy);
        System.out.println(context.buildResult(context.search(searchQuery)));
    }
}
