package com.nbugaenco.encryptdecrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<String, String> params = new HashMap<>() {{
        put("-mode", "enc");
        put("-key", "0");
        put("-data", "");
        put("-alg", "shift");
        put("-in", null);
        put("-out", null);
    }};

    public static void main(String[] args) {
        checkArgs(args);

        EncoderFactory encoderFactory = new EncoderFactory();
        Encoder encoder = encoderFactory.create(params.get("-alg"));

        encoder.setKey(Integer.parseInt(params.get("-key")));

        if (params.get("-data") != null && params.get("-data").length() > 0) {
            encoder.setPrompt(params.get("-data"));
        } else if (params.get("-in") != null && params.get("-in").length() > 0) {
            String inPath = params.get("-in");

            try {
                encoder.setPrompt(new String(Files.readAllBytes(Paths.get(inPath))));
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        } else {
            System.exit(0);
        }

        if (params.get("-out") != null && params.get("-out").length() > 0) {
            String outPath = params.get("-out");

            try (FileWriter fileWriter = new FileWriter(outPath)) {
                fileWriter.write(
                        (params.get("-mode").equals("enc")) ? encoder.encode() : encoder.decode()
                );
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        } else {
            System.out.println(
                    (params.get("-mode").equals("enc")) ? encoder.encode() : encoder.decode()
            );
        }
    }

    private static void checkArgs(String[] args) {
        for (int i = 0; i < args.length - 1; ++i) {
            switch (args[i]) {
                case "-mode" -> {
                    if (args[i + 1].equals("enc") || args[i + 1].equals("dec")) {
                        params.replace(args[i], args[i + 1]);
                    } else {
                        System.out.println("Wrong mode!\nModes available: enc, dec\nYour mode is set to 'enc'\n");
                    }
                }

                case "-key" -> {
                    try {
                        int key = Integer.parseInt(args[i + 1]);
                        params.replace(args[i], Integer.toString(key));
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong key!\nIt must be an integer number.\nYour key is set to 0\n");
                    }
                }

                case "-alg" -> {
                    if (args[i + 1].equals("shift") || args[i + 1].equals("unicode")) {
                        params.replace(args[i], args[i + 1]);
                    } else {
                        System.out.println("Wrong algorithm!\nAlgorithms available: shift, unicode\nYour mode is set to 'shift'");
                    }
                }

                case "-data", "-in", "-out" -> params.replace(args[i], args[i + 1]);

                default -> {
                    if (args[i].charAt(0) == '-') {
                        System.out.println("There is no parameter \"" + args[i] + "\"\n");
                        System.out.println(helpMessage());
                        System.exit(0);
                    }
                }
            }
        }

    }

    private static String helpMessage() {
        return """
                Here is the list of parameters:
                    -mode : enc, dec
                    -key : integer value;
                    -data : "string prompt"
                    -alg : shift, unicode
                    -in : "path/to/file"
                    -out : "path/to/file"
                """;
    }
}
