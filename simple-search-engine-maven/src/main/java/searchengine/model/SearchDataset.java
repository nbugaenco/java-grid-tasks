package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Class that stores data to search in and indexes for it
 */
@Getter
@Setter
public class SearchDataset {
    private List<String> lines;
    private Map<String, Set<Integer>> invertedIndexes;

    public SearchDataset(List<String> lines) {
        this.lines = lines;
        this.invertedIndexes = this.invertIndexes();
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        this.invertedIndexes = this.invertIndexes();
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
