package searchengine.model;

import java.util.*;

/**
 * Class that stores data to search in and indexes for it
 */
public class SearchDataset {
    private final Map<String, Set<Integer>> invertedIndexes;
    private final List<String> lines;

    public SearchDataset(List<String> lines) {
        this.lines = lines;
        this.invertedIndexes = this.invertIndexes(this.getLines());
    }

    public Map<String, Set<Integer>> getInvertedIndexes() {
        return Collections.unmodifiableMap(invertedIndexes);
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public void update(String... newLines) {
        this.lines.addAll(Arrays.asList(newLines));

        Map<String, Set<Integer>> newIndexes = this.invertIndexes(this.getLines());
        this.invertedIndexes.putAll(newIndexes);
    }

    /**
     * Indexing data in {@link SearchDataset}
     *
     * @return {@link Map} with all indexed data
     */
    private Map<String, Set<Integer>> invertIndexes(final List<String> input) {
        Map<String, Set<Integer>> indexes = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] tokens = line.split("\\s+");

            for (String token : tokens) {
                indexes.putIfAbsent(token.toLowerCase(), new HashSet<>());
                indexes.get(token.toLowerCase()).add(i);
            }
        }

        return indexes;
    }
}
