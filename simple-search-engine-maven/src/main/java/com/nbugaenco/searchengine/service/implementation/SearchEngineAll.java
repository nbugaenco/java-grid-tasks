package com.nbugaenco.searchengine.service.implementation;

import com.nbugaenco.searchengine.model.SearchDataset;
import com.nbugaenco.searchengine.service.SearchEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link SearchEngine} that searches lines that contain all words from search query
 */
public class SearchEngineAll extends SearchEngine {

    public SearchEngineAll(SearchDataset dataset) {
        super(dataset);
    }

    @Override
    public Set<Integer> search(String searchQuery) {
        // Indexes of lines that contain at least one word from search query
        List<Set<Integer>> queryIndexes = splitSearchQuery(searchQuery).stream()
                .map(word -> dataset.getInvertedIndexes().get(word.toLowerCase()))
                .toList();

        if (queryIndexes.isEmpty() || queryIndexes.get(0) == null) {
            return Set.of();
        }

        Set<Integer> indexes = new HashSet<>(queryIndexes.get(0));

        // Making intersection to get a set that contains only lines with all words from sq
        for (int i = 1; i < queryIndexes.size(); i++) {
            indexes.retainAll(queryIndexes.get(i));
        }

        return indexes;
    }
}
