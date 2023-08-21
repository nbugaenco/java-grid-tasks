package com.nbugaenco.searchengine.service.implementation;

import com.nbugaenco.searchengine.model.SearchDataset;
import com.nbugaenco.searchengine.service.SearchEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
        IntStream.range(0, dataset.getLines().size()).forEach(indexes::add);

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
