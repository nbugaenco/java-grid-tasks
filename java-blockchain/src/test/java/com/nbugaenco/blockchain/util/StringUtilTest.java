package com.nbugaenco.blockchain.util;

import com.nbugaenco.blockchain.exception.Sha256ProcessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTest {

    @Test
    @DisplayName("SHA-256 valid input")
    void applySha256_ValidInput() {
        // given
        String input = "Hello, World!";
        String expectedHash = "dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f";

        // when
        String actualHash = StringUtil.applySha256(input);

        // then
        assertEquals(expectedHash, actualHash);
    }

    @Test
    @DisplayName("SHA-256 empty input")
    void applySha256_EmptyInput() {
        // given
        String input = "";
        String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        // when
        String actualHash = StringUtil.applySha256(input);

        // then
        assertEquals(expectedHash, actualHash);
    }

    @Test
    @DisplayName("SHA-256 null string")
    void applySha256_ThrowsSha256ProcessException() {
        // given
        String input = null;

        // then
        assertThrows(Sha256ProcessException.class, () -> StringUtil.applySha256(input));
    }
}
