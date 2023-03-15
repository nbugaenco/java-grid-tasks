package com.nbugaenco.searchengine.service;

import com.nbugaenco.searchengine.model.SearchDataset;

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

    public abstract Set<Integer> search(String searchQuery);

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
}
