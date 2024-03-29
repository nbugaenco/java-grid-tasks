package com.nbugaenco.searchengine.service.implemetation;

import com.nbugaenco.searchengine.model.SearchDataset;
import com.nbugaenco.searchengine.service.SearchEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.nbugaenco.searchengine.service.implementation.SearchEngineAll;

import java.util.ArrayList;
import java.util.Set;

class SearchEngineAllTest {

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
    @DisplayName("Search with ALL strategy")
    void searchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineAll(dataset);
        Set<Integer> result = searchEngine.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(0);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Search with ALL strategy with non satisfying query")
    void emptySearchTest() {
        // when
        SearchEngine searchEngine = new SearchEngineAll(dataset);
        Set<Integer> result = searchEngine.search("Fernando Gray");

        // then
        Assertions.assertEquals(Set.of(), result);
    }
}
