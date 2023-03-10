package com.nbugaenco.searchengine;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that stores data to search in and indexes for it
 */
public class SearchDataset {
    private List<String> lines;
    private Map<String, Set<Integer>> invertedIndexes;

    public SearchDataset(List<String> lines) {
        this.lines = lines;
        invertIndexes();
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        invertIndexes();
    }

    public Map<String, Set<Integer>> getInvertedIndexes() {
        return invertedIndexes;
    }

    public void setInvertedIndexes(Map<String, Set<Integer>> invertedIndexes) {
        this.invertedIndexes = invertedIndexes;
    }

    public void invertIndexes() {
        invertedIndexes = SearchEngineUtils.invertIndexes(lines);
    }
}
