package com.nbugaenco.encryptdecrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utilities for Encoder
 */
public class EncoderUtils {

    /**
     * Encoding process based on CLI arguments
     * @param params given parameters for processing. Check {@link Parameter}
     * @param encoder {@link Encoder} to use. Type of encoder is based on {@link Parameter#ALG}
     */
    public static void processEncode(Map<Parameter, String> params, Encoder encoder) {
        encoder.setKey(Integer.parseInt(params.get(Parameter.KEY)));

        // Setting prompt to manipulate
        // Data is preferred over a source file, so it goes first
        if (params.get(Parameter.DATA) != null && params.get(Parameter.DATA).length() > 0) {
            encoder.setPrompt(params.get(Parameter.DATA));
        }
        // If there is no data, but source path, read data from it
        else if (params.get(Parameter.IN) != null && params.get(Parameter.IN).length() > 0) {
            String inPath = params.get(Parameter.IN);

            try {
                encoder.setPrompt(Files.readString(Paths.get(inPath)));
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        }
        // If neither data nor source file is present, just close application
        else {
            System.exit(0);
        }

        // File output is preferred over a console one, so it goes first
        if (params.get(Parameter.OUT) != null && params.get(Parameter.OUT).length() > 0) {
            String outPath = params.get(Parameter.OUT);

            try (FileWriter fileWriter = new FileWriter(outPath)) {
                // Based on "-mode" will be chosen encoding or decoding method
                fileWriter.write(
                        (params.get(Parameter.MODE).equals("enc")) ? encoder.encode() : encoder.decode()
                );
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        }
        else {
            // Based on "-mode" will be chosen encoding or decoding method
            System.out.println(
                    (params.get(Parameter.MODE).equals("enc")) ? encoder.encode() : encoder.decode()
            );
        }
    }

    /**
     * This method parsing parameters from CLI and creating {@code Map<Parameter, String>}.
     * Based on {@link Parameter}
     * @param args CLI arguments from {@link Main#main(String[] args)}
     * @return {@link HashMap} with parsed arguments
     */
    public static Map<Parameter, String> getParams(String[] args) {
        Map<Parameter, String> params = new HashMap<>();

        if (args.length == 0) System.exit(0);

        // Print help message
        if (args.length == 1 && Objects.requireNonNull(Parameter.valueOfLabel(args[0])) == Parameter.HELP)
            System.out.println(helpMessage());

        for (int i = 0; i < args.length - 1; ++i) {
            Parameter arg = Objects.requireNonNull(Parameter.valueOfLabel(args[i]));

            switch (arg) {
                case MODE -> {
                    if (args[i + 1].equals("enc") || args[i + 1].equals("dec")) {
                        params.putIfAbsent(arg, args[i + 1]);
                    } else {
                        System.out.println("Wrong mode!\nModes available: enc, dec\nYour mode is set to 'enc'\n");
                    }
                }

                case KEY -> {
                    try {
                        int key = Integer.parseInt(args[i + 1]);
                        params.putIfAbsent(arg, Integer.toString(key));
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong key!\nIt must be an integer number.\nYour key is set to 0\n");
                    }
                }

                case ALG -> {
                    if (args[i + 1].equals("shift") || args[i + 1].equals("unicode")) {
                        params.putIfAbsent(arg, args[i + 1]);
                    } else {
                        System.out.println("Wrong algorithm!\nAlgorithms available: shift, unicode\nYour mode is set to 'shift'\n");
                    }
                }

                case DATA, IN, OUT -> params.putIfAbsent(arg, args[i + 1]);

                default -> {
                    if (args[i].charAt(0) == '-') {
                        System.out.println("There is no parameter \"" + args[i] + "\"\n");
                        System.out.println(helpMessage());
                        System.exit(0);
                    }
                }
            }

            ++i;
        }

        // Default values
        params.putIfAbsent(Parameter.MODE, "enc");
        params.putIfAbsent(Parameter.KEY, "0");
        params.putIfAbsent(Parameter.ALG, "shift");

        return params;
    }

    /**
     * String with help message about using this program
     * @return {@link String} with help message
     */
    private static String helpMessage() {
        return """
                Here is the list of parameters:
                    -mode : enc, dec
                    -key : integer value;
                    -data : "string prompt"
                    -alg : shift, unicode
                    -in : "path/to/file"
                    -out : "path/to/file"
                    
                    To print this message call -help argument only
                """;
    }
}
