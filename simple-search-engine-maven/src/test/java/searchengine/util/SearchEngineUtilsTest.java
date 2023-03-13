package searchengine.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchEngineUtilsTest {

    @Test
    @DisplayName("Test invalid CLI arguments")
    void readFileWithWrongArgs() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> SearchEngineUtils.readFile(new String[]{"--daa", "text.txt"}));

        Assertions.assertTrue(ex.getMessage().contains("Invalid argument"));
    }
}
