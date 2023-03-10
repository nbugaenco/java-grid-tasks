package searchengine.model;

import lombok.Data;
import searchengine.util.SearchEngineUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that stores data to search in and indexes for it
 */
@Data
public class SearchDataset {
    private List<String> lines;
    private Map<String, Set<Integer>> invertedIndexes;

    public SearchDataset(List<String> lines) {
        this.lines = lines;
        invertIndexes();
    }

    public void invertIndexes() {
        invertedIndexes = SearchEngineUtils.invertIndexes(lines);
    }
}
