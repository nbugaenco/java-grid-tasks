package com.nbugaenco.encryptdecrypt.util;

import com.nbugaenco.encryptdecrypt.exception.PromptIOException;
import com.nbugaenco.encryptdecrypt.model.Encoder;
import com.nbugaenco.encryptdecrypt.model.UnicodeEncoder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import static com.nbugaenco.encryptdecrypt.util.Parameter.*;
import static org.junit.jupiter.api.Assertions.*;

class EncoderUtilsTest {

    String[] args;

    @Test
    void createDefaultParams() {
        // given
        args = new String[]{
                "-data", "Hello, World!"
        };

        Map<Parameter, String> expectedParams = new EnumMap<>(Parameter.class) {{
            put(MODE, "enc");
            put(KEY, "0");
            put(ALG, "shift");
            put(DATA, "Hello, World!");
        }};

        // when then
        assertEquals(expectedParams, EncoderUtils.getParams(args));
    }

    @Test
    void createSpecifiedParams() {
        // given
        args = new String[]{
                "-data", "Hello, World!",
                "-key", "5",
                "-alg", "unicode",
                "-mode", "dec",
        };

        Map<Parameter, String> expectedParams = new EnumMap<>(Parameter.class) {{
            put(DATA, "Hello, World!");
            put(KEY, "5");
            put(ALG, "unicode");
            put(MODE, "dec");
        }};

        // when then
        assertEquals(expectedParams, EncoderUtils.getParams(args));
    }

    @Test
    void createWithInvalidParamsValues() {
        // given
        args = new String[]{
                "-data", "Hello, World!",
                "-key", "not a number",
                "-alg", "wrong algorithm",
                "-mode", "wrong mode",
        };

        // when
        Map<Parameter, String> params = EncoderUtils.getParams(args);

        // then
        assertSame("0", params.get(KEY));
        assertSame("shift", params.get(ALG));
        assertSame("enc", params.get(MODE));
    }

    @Test
    void processSuccessEncodeToFile() {
        // given
        Map<Parameter, String> params = new EnumMap<>(Parameter.class) {{
            put(DATA, "Hello, World!");
            put(KEY, "5");
            put(ALG, "unicode");
            put(MODE, "enc");
            put(OUT, "out.txt");
        }};

        Encoder encoder = new UnicodeEncoder();

        // when
        EncoderUtils.processEncode(params, encoder);
        File output = new File("out.txt");

        // then
        assertTrue(output.delete());
    }

    @Test
    void processSuccessReadPromptFromFile() throws IOException {
        // given
        File inputFile = new File("in.txt");

        FileWriter fileWriter = new FileWriter(inputFile);
        fileWriter.write("Hello, File!");
        fileWriter.close();

        Map<Parameter, String> params = new EnumMap<>(Parameter.class) {{
            put(IN, "in.txt");
        }};

        // when then
        assertEquals("Hello, File!", EncoderUtils.readPromptFromArgs(params));
        assertTrue(inputFile.delete());
    }

    @Test
    void processFailReadPromptFromFile() {
        // given
        Map<Parameter, String> params = new EnumMap<>(Parameter.class) {{
            put(IN, "in.txt");
        }};

        // when then
        assertThrows(PromptIOException.class, () -> EncoderUtils.readPromptFromArgs(params));
    }
}
