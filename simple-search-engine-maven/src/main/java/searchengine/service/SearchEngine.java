package searchengine.service;

import searchengine.model.SearchDataset;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Basic abstract class for SearcEngine strategies
 */
public abstract class SearchEngine {
    protected final SearchDataset dataset;

    protected SearchEngine(SearchDataset dataset) {
        this.dataset = dataset;
    }

    public abstract String search(String searchQuery);

    /**
     * This method splits search query into words
     *
     * @param searchQuery the search query
     * @return a list of words
     */
    protected List<String> splitSearchQuery(String searchQuery) {
        return Arrays.stream(searchQuery.split("\\s+"))
                .map(String::toLowerCase)
                .toList();
    }

    /**
     * This method makes {@link String} with result of search query
     *
     * @param dataset Dataset to search in
     * @param indexes Lines indexes that satisfy the search query
     * @return result of search query process
     */
    protected String buildResult(SearchDataset dataset, Set<Integer> indexes) {
        if (indexes.isEmpty()) {
            return "\nNo matching people found.";
        }

        StringBuilder stringBuilder = new StringBuilder("\n=== Found people ===\n");

        for (Integer index : indexes) {
            stringBuilder.append(dataset.getLines().get(index)).append("\n");
        }

        return stringBuilder.toString();
    }
}
