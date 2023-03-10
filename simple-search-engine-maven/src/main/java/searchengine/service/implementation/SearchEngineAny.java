package searchengine.service.implementation;

import searchengine.model.SearchDataset;
import searchengine.service.SearchEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link SearchEngine} that searches lines that contain at least one word from search query
 */
public class SearchEngineAny implements SearchEngine {

    @Override
    public String search(SearchDataset dataset, String searchQuery) {
        List<String> words = splitSearchQuery(searchQuery);
        Set<Integer> indexes = new HashSet<>(words.size());

        // Search for lines that contain at least one word from search query, or empty set if no words are found
        for (String word : words) {
            indexes.addAll(dataset.getInvertedIndexes().getOrDefault(word, new HashSet<>()));
        }

        return buildResult(dataset, indexes);
    }
}
