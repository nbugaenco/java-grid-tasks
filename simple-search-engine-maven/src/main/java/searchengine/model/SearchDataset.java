package searchengine.model;

import lombok.Getter;

import java.util.*;

/**
 * Class that stores data to search in and indexes for it
 */
@Getter
public class SearchDataset {
    private final Map<String, Set<Integer>> invertedIndexes;
    private final List<String> lines;

    public SearchDataset(List<String> lines) {
        this.lines = lines;
        this.invertedIndexes = this.invertIndexes();
    }

    public void update(String... lines) {
        this.lines.addAll(Arrays.asList(lines));

        Map<String, Set<Integer>> newIndexes = this.invertIndexes();
        this.invertedIndexes.putAll(newIndexes);
    }

    public Map<String, Set<Integer>> getInvertedIndexes() {
        return Collections.unmodifiableMap(invertedIndexes);
    }

    /**
     * Indexing data in {@link SearchDataset}
     *
     * @return {@link Map} with all indexed data
     */
    private Map<String, Set<Integer>> invertIndexes() {
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
}
