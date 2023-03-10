package searchengine.model;

import lombok.Data;
import searchengine.service.SearchEngine;
import searchengine.service.implementation.SearchEngineAll;
import searchengine.service.implementation.SearchEngineAny;
import searchengine.service.implementation.SearchEngineNone;

/**
 * Context for {@link SearchEngine} implementations
 */
@Data
public class SearchEngineContext {
    private SearchEngine searchMethod;

    // Factory for the poor
    public SearchEngineContext(SearchStrategy strategy) {
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

    public String search(SearchDataset dataset, String searchQuery) {
        return this.searchMethod.search(dataset, searchQuery);
    }
}
