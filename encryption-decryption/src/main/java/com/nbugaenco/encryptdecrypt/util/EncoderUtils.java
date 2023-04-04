package com.nbugaenco.encryptdecrypt.util;

import com.nbugaenco.encryptdecrypt.Main;
import com.nbugaenco.encryptdecrypt.exception.PromptIOException;
import com.nbugaenco.encryptdecrypt.model.Encoder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.nbugaenco.encryptdecrypt.util.Parameter.*;

/**
 * Utilities for Encoder
 */
public class EncoderUtils {

    public static final String HELP_MESSAGE = """
            Here is the list of parameters:
                -mode : enc, dec
                -key : integer value;
                -data : "string prompt"
                -alg : shift, unicode
                -in : "path/to/file"
                -out : "path/to/file"
                
                To print this message call -help argument only
            """;

    /**
     * Private constructor to prevent utility class instantiation
     */
    private EncoderUtils() {
        throw new IllegalStateException("Util class cannot be instantiated");
    }

    /**
     * Encoding process based on CLI arguments
     *
     * @param params  given parameters for processing. Check {@link Parameter}
     * @param encoder {@link Encoder} to use. Type of encoder is based on {@link Parameter#ALG}
     */
    public static void processEncode(Map<Parameter, String> params, Encoder encoder) {
        encoder.setKey(Integer.parseInt(params.get(KEY)));
        encoder.setPrompt(readPromptFromArgs(params));

        // File output is preferred over a console one, so it goes first
        if (params.get(OUT) != null && params.get(OUT).length() > 0) {
            outputToFile(params, encoder);
        } else {
            outputToConsole(params, encoder);
        }
    }

    /**
     * Outputs encoder result to console
     *
     * @param params  CLI arguments
     * @param encoder encoder to encrypt/decrypt
     */
    private static void outputToConsole(Map<Parameter, String> params, Encoder encoder) {
        // Based on "-mode" will be chosen encoding or decoding method
        System.out.println(
                (params.get(MODE).equals("enc")) ? encoder.encode() : encoder.decode()
        );
    }

    /**
     * Outputs encoder result to file
     *
     * @param params  CLI arguments
     * @param encoder encoder to encrypt/decrypt
     */
    private static void outputToFile(Map<Parameter, String> params, Encoder encoder) {
        String outPath = params.get(OUT);

        try (FileWriter fileWriter = new FileWriter(outPath)) {
            // Based on "-mode" will be chosen encoding or decoding method
            fileWriter.write(
                    (params.get(MODE).equals("enc")) ? encoder.encode() : encoder.decode()
            );
        } catch (IOException e) {
            System.out.println("File error!");
            System.exit(0);
        }
    }

    /**
     * Returns prompt from CLI arguments
     *
     * @param params CLI arguments
     * @return prompt String
     */
    public static String readPromptFromArgs(Map<Parameter, String> params) {
        // Setting prompt to manipulate
        // Data is preferred over a source file, so it goes first
        if (params.get(DATA) != null && params.get(DATA).length() > 0) {
            return params.get(DATA);
        }
        // If there is no data, but source path, read data from it
        else if (params.get(IN) != null && params.get(IN).length() > 0) {
            String inPath = params.get(IN);

            try {
                return Files.readString(Paths.get(inPath));
            } catch (IOException e) {
                throw new PromptIOException("File error, can't read prompt");
            }
        }
        // If neither data nor source file is present, just close application
        else {
            System.exit(0);
        }

        return null;
    }

    /**
     * This method parsing parameters from CLI and creating {@code Map<Parameter, String>}.
     * Based on {@link Parameter}
     *
     * @param args CLI arguments from {@link Main#main(String[] args)}
     * @return {@link HashMap} with parsed arguments
     */
    public static Map<Parameter, String> getParams(String[] args) {
        Map<Parameter, String> params = new EnumMap<>(Parameter.class);

        if (args.length == 0) System.exit(0);

        // Print help message
        if (args.length == 1 && Objects.requireNonNull(valueOfLabel(args[0])) == HELP) {
            System.out.println(HELP_MESSAGE);
        }

        for (int i = 0; i < args.length - 1; i += 2) {
            Parameter arg = Objects.requireNonNull(valueOfLabel(args[i]));

            switch (arg) {
                case MODE -> chooseMode(args, params, i, arg);

                case KEY -> chooseKey(args, params, i, arg);

                case ALG -> chooseAlg(args, params, i, arg);

                case DATA, IN, OUT -> params.putIfAbsent(arg, args[i + 1]);

                default -> {
                    if (args[i].charAt(0) == '-') {
                        System.out.println(HELP_MESSAGE);
                        throw new IllegalArgumentException("Invalid parameter \"" + args[i] + "\"");
                    }
                }
            }
        }

        // Default values
        params.putIfAbsent(MODE, "enc");
        params.putIfAbsent(KEY, "0");
        params.putIfAbsent(ALG, "shift");

        return params;
    }

    private static void chooseAlg(String[] args, Map<Parameter, String> params, int i, Parameter arg) {
        if (args[i + 1].equals("shift") || args[i + 1].equals("unicode")) {
            params.putIfAbsent(arg, args[i + 1]);
        } else {
            System.out.println("Wrong algorithm!\nAlgorithms available: shift, unicode\nYour mode is set to 'shift'\n");
        }
    }

    private static void chooseKey(String[] args, Map<Parameter, String> params, int i, Parameter arg) {
        try {
            int key = Integer.parseInt(args[i + 1]);
            params.putIfAbsent(arg, Integer.toString(key));
        } catch (NumberFormatException e) {
            System.out.println("Wrong key!\nIt must be an integer number.\nYour key is set to 0\n");
        }
    }

    private static void chooseMode(String[] args, Map<Parameter, String> params, int i, Parameter arg) {
        if (args[i + 1].equals("enc") || args[i + 1].equals("dec")) {
            params.putIfAbsent(arg, args[i + 1]);
        } else {
            System.out.println("Wrong mode!\nModes available: enc, dec\nYour mode is set to 'enc'\n");
        }
    }
}
