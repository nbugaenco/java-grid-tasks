package searchengine.util;

import searchengine.model.SearchEngineContext;
import searchengine.model.SearchStrategy;
import searchengine.model.SearchDataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
     * Indexing data in {@link SearchDataset}
     *
     * @param lines from {@link SearchDataset} to index
     * @return {@link Map} with all indexed data
     */
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
                System.exit(0);
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
     * @param dataset {@link SearchDataset} that contains needed data
     * @param choice  Menu item that user has chosen
     */
    public static void processMenuItem(SearchDataset dataset, byte choice) {
        switch (choice) {
            case 1 -> findPerson(dataset);
            case 2 -> {
                System.out.println("\n=== List of people ===");
                dataset.getLines().forEach(System.out::println);
            }
            case 0 -> System.out.println("\nBye!");
            default -> System.out.println("\nIncorrect option! Try again.");
        }
    }

    /**
     * Method for searching some data from given {@link SearchDataset} (is the 1st menu item)
     *
     * @param dataset {@link SearchDataset} to search in
     */
    private static void findPerson(SearchDataset dataset) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nSelect a matching strategy (ALL, ANY, NONE): ");
        SearchStrategy searchMethod = SearchStrategy.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("\nEnter a name or email to search all suitable people:");
        String searchQuery = scanner.nextLine();

        SearchEngineContext searchEngineContext = new SearchEngineContext(searchMethod);
        System.out.println(searchEngineContext.search(dataset, searchQuery));
    }
}
