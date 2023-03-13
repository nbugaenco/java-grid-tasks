package searchengine.service.implemetation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import searchengine.model.SearchDataset;
import searchengine.service.SearchEngine;
import searchengine.service.implementation.SearchEngineAny;

import java.util.ArrayList;
import java.util.Set;

class SearchEngineAnyTest {

    SearchDataset dataset = new SearchDataset(new ArrayList<>() {
        {
            add("Kristofer Gray");
            add("Fernando Marbury fernando_marbury@gmail.com");
            add("Kristyn Nix nix-kris@gmail.com");
            add("Regenia Enderle");
            add("Kyle Gray");
        }
    });

    @Test
    @DisplayName("Search with ANY strategy")
    void searchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineAny(dataset);
        Set<Integer> result = searchEngine.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(0, 4);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Search with ANY strategy with non satisfying query")
    void emptySearchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineAny(dataset);
        Set<Integer> result = searchEngine.search("whatever");

        // then
        Assertions.assertEquals(Set.of(), result);
    }
}
