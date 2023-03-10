package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import searchengine.service.SearchEngine;
import searchengine.service.implementation.SearchEngineAll;
import searchengine.service.implementation.SearchEngineAny;
import searchengine.service.implementation.SearchEngineNone;

import java.util.Objects;

/**
 * Context for {@link SearchEngine} implementations
 */
@Getter
@Setter
public class SearchEngineContext {
    private SearchEngine searchMethod;

    private SearchDataset dataset;

    public SearchEngineContext(SearchDataset dataset) {
        this.dataset = dataset;
        this.searchMethod = null;
    }

    // Factory for the poor
    public SearchEngineContext(SearchStrategy strategy, SearchDataset dataset) {
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
            if (strategy == SearchStrategy.ALL && searchMethod.getClass() != SearchEngineAll.class) {
                this.searchMethod = new SearchEngineAll(dataset);
            } else if (strategy == SearchStrategy.ANY && searchMethod.getClass() != SearchEngineAny.class) {
                this.searchMethod = new SearchEngineAny(dataset);
            } else if (strategy == SearchStrategy.NONE && searchMethod.getClass() != SearchEngineNone.class) {
                this.searchMethod = new SearchEngineNone(dataset);
            }
        }
    }

    public String search(String searchQuery) {
        return this.searchMethod.search(searchQuery);
    }
}
