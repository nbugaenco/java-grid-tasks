package com.nbugaenco.searchengine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.nbugaenco.searchengine.service.implementation.SearchEngineAll;
import com.nbugaenco.searchengine.service.implementation.SearchEngineAny;
import com.nbugaenco.searchengine.service.implementation.SearchEngineNone;

import java.util.ArrayList;
import java.util.Set;

class SearchEngineContextTest {

    private SearchDataset dataset;

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
    @DisplayName("Create context w/o SearchMethod (Search Engine)")
    void createContext() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset);

        // then
        Assertions.assertNotNull(context.getDataset());
        Assertions.assertNull(context.getSearchMethod());
    }

    @Test
    @DisplayName("Create context with SearchEngineAll")
    void createContextWithSearchMethodAll() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ALL);

        // then
        Assertions.assertNotNull(context.getDataset());
        Assertions.assertInstanceOf(SearchEngineAll.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Create context with SearchEngineAny")
    void createContextWithSearchMethodAny() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ANY);

        // then
        Assertions.assertNotNull(context.getDataset());
        Assertions.assertInstanceOf(SearchEngineAny.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Create context with SearchEngineNone")
    void createContextWithSearchMethodNone() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.NONE);

        // then
        Assertions.assertNotNull(context.getDataset());
        Assertions.assertInstanceOf(SearchEngineNone.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Create context with not existing search engine")
    void createContextWithWrongSearchMethod() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SearchEngineContext(dataset, SearchStrategy.valueOf("WHATEVER")));

        Assertions.assertTrue(ex.getMessage().contains("No enum constant"));
    }

    @Test
    @DisplayName("Set SearchEngineAll when it's initially null")
    void setSearchMethodAll() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset);
        context.setSearchMethod(SearchStrategy.ALL);

        // then
        Assertions.assertInstanceOf(SearchEngineAll.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Set SearchEngineAny when it's initially null")
    void setSearchMethodAny() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset);
        context.setSearchMethod(SearchStrategy.ANY);

        // then
        Assertions.assertInstanceOf(SearchEngineAny.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Set SearchEngineNone when it's initially null")
    void setSearchMethodNone() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset);
        context.setSearchMethod(SearchStrategy.NONE);

        // then
        Assertions.assertInstanceOf(SearchEngineNone.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Set SearchEngineAll when it's already is set")
    void changeSearchMethodToAll() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.NONE);
        context.setSearchMethod(SearchStrategy.ALL);

        // then
        Assertions.assertFalse(context.getSearchMethod() instanceof SearchEngineNone);
        Assertions.assertInstanceOf(SearchEngineAll.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Set SearchEngineAny when it's already is set")
    void changeSearchMethodToAny() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.NONE);
        context.setSearchMethod(SearchStrategy.ANY);

        // then
        Assertions.assertFalse(context.getSearchMethod() instanceof SearchEngineNone);
        Assertions.assertInstanceOf(SearchEngineAny.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Set SearchEngineNone when it's already is set")
    void changeSearchMethodToNone() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ALL);
        context.setSearchMethod(SearchStrategy.NONE);

        // then
        Assertions.assertFalse(context.getSearchMethod() instanceof SearchEngineAll);
        Assertions.assertInstanceOf(SearchEngineNone.class, context.getSearchMethod());
    }

    @Test
    @DisplayName("Try search when SearchEngine is null")
    void searchWithoutSearchMethod() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset);

        // then
        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> context.search("Kristofer"));

        Assertions.assertEquals("Search method is null", ex.getMessage());
    }

    @Test
    @DisplayName("Search with SearchEngineAll")
    void searchWithSearchMethodAll() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ALL);
        Set<Integer> result = context.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(0);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Search with SearchEngineAny")
    void searchWithSearchMethodAny() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ANY);
        Set<Integer> result = context.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(0, 4);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Search with SearchEngineNone")
    void searchWithSearchMethodNone() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.NONE);
        Set<Integer> result = context.search("Kristofer Gray");

        // then
        Set<Integer> expected = Set.of(1, 2, 3);
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check result String correctness")
    void buildResultTest() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ALL);
        String result = context.buildResult(context.search("Kristofer Gray"));

        // then
        String expected = "\n=== Found people ===\nKristofer Gray\n";
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Check result String correctness when nothing found")
    void emptyBuildResultTest() {
        // when
        SearchEngineContext context = new SearchEngineContext(dataset, SearchStrategy.ALL);
        String result = context.buildResult(context.search("Ana Gray"));

        // then
        String expected = "\nNo matching people found.";
        Assertions.assertEquals(expected, result);
    }
}
