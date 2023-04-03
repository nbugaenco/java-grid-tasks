package com.nbugaenco.searchengine.model;

import com.nbugaenco.searchengine.service.SearchEngine;
import com.nbugaenco.searchengine.service.implementation.SearchEngineNone;
import lombok.Getter;
import com.nbugaenco.searchengine.service.implementation.SearchEngineAll;
import com.nbugaenco.searchengine.service.implementation.SearchEngineAny;

import java.util.Objects;
import java.util.Set;

/**
 * Context for {@link SearchEngine} implementations
 */
@Getter
public class SearchEngineContext {
    private final SearchDataset dataset;
    private SearchEngine searchMethod;

    public SearchEngineContext(SearchDataset dataset) {
        this.dataset = dataset;
        this.searchMethod = null;
    }

    // Factory for the poor
    public SearchEngineContext(SearchDataset dataset, SearchStrategy strategy) {
        this.dataset = dataset;

        if (strategy == SearchStrategy.ALL) {
            this.searchMethod = new SearchEngineAll(dataset);
        } else if (strategy == SearchStrategy.ANY) {
            this.searchMethod = new SearchEngineAny(dataset);
        } else if (strategy == SearchStrategy.NONE) {
            this.searchMethod = new SearchEngineNone(dataset);
        } else {
            throw new IllegalArgumentException("Invalid search strategy");
        }
    }

    public void setSearchMethod(SearchStrategy strategy) {
        if (!Objects.nonNull(searchMethod)) {
            switch (strategy) {
                case ALL -> this.searchMethod = new SearchEngineAll(dataset);
                case ANY -> this.searchMethod = new SearchEngineAny(dataset);
                case NONE -> this.searchMethod = new SearchEngineNone(dataset);
                default -> throw new IllegalArgumentException("Invalid search strategy");
            }
        } else {
            if (strategy == SearchStrategy.ALL && !(searchMethod instanceof SearchEngineAll)) {
                this.searchMethod = new SearchEngineAll(dataset);
            } else if (strategy == SearchStrategy.ANY && !(searchMethod instanceof SearchEngineAny)) {
                this.searchMethod = new SearchEngineAny(dataset);
            } else if (strategy == SearchStrategy.NONE && !(searchMethod instanceof SearchEngineNone)) {
                this.searchMethod = new SearchEngineNone(dataset);
            } else {
                throw new IllegalArgumentException("Invalid search strategy");
            }
        }
    }

    public Set<Integer> search(String searchQuery) {
        if (Objects.isNull(searchMethod)) {
            throw new IllegalStateException("Search method is null");
        }
        return this.searchMethod.search(searchQuery);
    }

    /**
     * This method makes {@link String} with result of search query
     *
     * @param indexes Lines indexes that satisfy the search query
     * @return result of search query process
     */
    public String buildResult(Set<Integer> indexes) {
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
