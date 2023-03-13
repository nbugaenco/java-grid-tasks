package searchengine.service.implementation;

import searchengine.model.SearchDataset;
import searchengine.service.SearchEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link SearchEngine} that searches lines that do NOT contain any words from search query
 */
public class SearchEngineNone extends SearchEngine {

    public SearchEngineNone(SearchDataset dataset) {
        super(dataset);
    }

    @Override
    public Set<Integer> search(String searchQuery) {
        List<String> words = splitSearchQuery(searchQuery);
        Set<Integer> indexes = new HashSet<>(dataset.getLines().size());

        // Adding all line's indexes
        for (int i = 0; i < dataset.getLines().size(); ++i) {
            indexes.add(i);
        }

        // Making set of indexes that contain words from search query
        Set<Integer> querySet = new HashSet<>();
        for (String word : words) {
            querySet.addAll(dataset.getInvertedIndexes().getOrDefault(word, new HashSet<>()));
        }

        // Subtracting  querySet from all indexes
        indexes.removeAll(querySet);

        return indexes;
    }
}
