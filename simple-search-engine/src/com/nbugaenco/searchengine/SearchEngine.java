package com.nbugaenco.searchengine;

/**
 * Context for {@link SearchEngineStrategy} implementations
 */
public class SearchEngine {
    private SearchEngineStrategy searchMethod;

    public SearchEngine(SearchEngineStrategy searchMethod) {
        this.searchMethod = searchMethod;
    }

    // Factory for the poor
    public SearchEngine(SearchStrategy strategy) {
        if (strategy == SearchStrategy.ALL) {
            this.searchMethod = new SearchEngineAll();
        } else if (strategy == SearchStrategy.ANY) {
            this.searchMethod = new SearchEngineAny();
        } else if (strategy == SearchStrategy.NONE) {
            this.searchMethod = new SearchEngineNone();
        } else {
            throw new IllegalArgumentException("Invalid search strategy");
        }
    }

    public void setSearchMethod(SearchEngineStrategy searchMethod) {
        this.searchMethod = searchMethod;
    }

    public String search(SearchDataset dataset, String searchQuery) {
        return this.searchMethod.search(dataset, searchQuery);
    }
}
