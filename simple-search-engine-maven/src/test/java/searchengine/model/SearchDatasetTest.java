package searchengine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SearchDatasetTest {

    private final List<String> lines = new ArrayList<>() {
        {
            add("Kristofer Galley");
            add("Fernando Marbury fernando_marbury@gmail.com");
            add("Kristyn Nix nix-kris@gmail.com");
            add("Regenia Enderle");
        }
    };

    private SearchDataset dataset;

    @BeforeEach
    void setUp() {
        dataset = new SearchDataset(lines);
    }

    @Test
    @DisplayName("Create dataset and check if indexes exist")
    void checkIndexes() {

        //then
        Assertions.assertTrue(dataset.getInvertedIndexes().containsKey("kristofer"));
        Assertions.assertFalse(dataset.getInvertedIndexes().containsKey("rrrnderlew"));
    }

    @Test
    @DisplayName("Add new line and check if index is present")
    void makeUpdate() {
        //when
        dataset.update("new");

        //then
        Assertions.assertTrue(dataset.getInvertedIndexes().containsKey("new"));
    }
}
