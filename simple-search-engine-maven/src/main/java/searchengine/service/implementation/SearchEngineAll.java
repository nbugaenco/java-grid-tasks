package searchengine.service.implementation;

import searchengine.model.SearchDataset;
import searchengine.service.SearchEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link SearchEngine} that searches lines that contain all words from search query
 */
public class SearchEngineAll implements SearchEngine {

    @Override
    public String search(SearchDataset dataset, String searchQuery) {
        List<String> words = splitSearchQuery(searchQuery);

        // Indexes of lines that contain at least one word from search query
        List<Set<Integer>> queryIndexes = new ArrayList<>(words.size());

        // Adding these indexes
        for (String word : words) {
            queryIndexes.add(dataset.getInvertedIndexes().get(word.toLowerCase()));
        }

        if (queryIndexes.isEmpty() || queryIndexes.get(0) == null) {
            return "\nNo matching people found.";
        }

        Set<Integer> indexes = new HashSet<>(queryIndexes.get(0));

        // Making intersection to get a set that contains only lines with all words from sq
        for (int i = 1; i < queryIndexes.size(); i++) {
            indexes.retainAll(queryIndexes.get(i));
        }

        return buildResult(dataset, indexes);
    }
}
