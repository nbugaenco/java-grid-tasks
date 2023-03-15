package com.nbugaenco.searchengine.service.implemetation;

import com.nbugaenco.searchengine.model.SearchDataset;
import com.nbugaenco.searchengine.service.SearchEngine;
import com.nbugaenco.searchengine.service.implementation.SearchEngineNone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

class SearchEngineNoneTest {

    SearchDataset dataset;

    @BeforeEach
    void setUp() {
        dataset = new SearchDataset(new ArrayList<>() {
            {
                add("Kristofer Gray");
                add("Fernando Marbury fernando_marbury@gmail.com");
                add("Kristyn Nix nix-kris@gmail.com");
                add("Regenia Enderle");
                add("Kyle Gray");
            }
        });
    }

    @Test
    @DisplayName("Search with NONE strategy")
    void searchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineNone(dataset);
        Set<Integer> result = searchEngine.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(1, 2, 3);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Search with NONE strategy with non satisfying query (return all lines)")
    void emptySearchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineNone(dataset);
        Set<Integer> result = searchEngine.search("whatever");

        // then
        Set<Integer> expected = Set.of(0, 1, 2, 3, 4);
        Assertions.assertEquals(expected, result);
    }
}
