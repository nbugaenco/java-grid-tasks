package com.nbugaenco.encryptdecrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    private static final Map<Parameter, String> params = new HashMap<>() {{
        put(Parameter.MODE, "enc");
        put(Parameter.KEY, "0");
        put(Parameter.DATA, "");
        put(Parameter.ALG, "shift");
        put(Parameter.IN, null);
        put(Parameter.OUT, null);
    }};

    public static void main(String[] args) {
        checkArgs(args);

        EncoderFactory encoderFactory = new EncoderFactory();
        Encoder encoder = encoderFactory.create(params.get(Parameter.ALG));

        encoder.setKey(Integer.parseInt(params.get(Parameter.KEY)));

        if (params.get(Parameter.DATA) != null && params.get(Parameter.DATA).length() > 0) {
            encoder.setPrompt(params.get(Parameter.DATA));
        } else if (params.get(Parameter.IN) != null && params.get(Parameter.IN).length() > 0) {
            String inPath = params.get(Parameter.IN);

            try {
                encoder.setPrompt(new String(Files.readAllBytes(Paths.get(inPath))));
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        } else {
            System.exit(0);
        }

        if (params.get(Parameter.OUT) != null && params.get(Parameter.OUT).length() > 0) {
            String outPath = params.get(Parameter.OUT);

            try (FileWriter fileWriter = new FileWriter(outPath)) {
                fileWriter.write(
                        (params.get(Parameter.MODE).equals("enc")) ? encoder.encode() : encoder.decode()
                );
            } catch (IOException e) {
                System.out.println("File error!");
                System.exit(0);
            }
        } else {
            System.out.println(
                    (params.get(Parameter.MODE).equals("enc")) ? encoder.encode() : encoder.decode()
            );
        }
    }

    private static void checkArgs(String[] args) {
        for (int i = 0; i < args.length - 1; ++i) {
            Parameter arg = Objects.requireNonNull(Parameter.valueOfLabel(args[i]));

            switch (arg) {
                case MODE -> {
                    if (args[i + 1].equals("enc") || args[i + 1].equals("dec")) {
                        params.replace(arg, args[i + 1]);
                    } else {
                        System.out.println("Wrong mode!\nModes available: enc, dec\nYour mode is set to 'enc'\n");
                    }
                }

                case KEY -> {
                    try {
                        int key = Integer.parseInt(args[i + 1]);
                        params.replace(arg, Integer.toString(key));
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong key!\nIt must be an integer number.\nYour key is set to 0\n");
                    }
                }

                case ALG -> {
                    if (args[i + 1].equals("shift") || args[i + 1].equals("unicode")) {
                        params.replace(arg, args[i + 1]);
                    } else {
                        System.out.println("Wrong algorithm!\nAlgorithms available: shift, unicode\nYour mode is set to 'shift'\n");
                    }
                }

                case DATA, IN, OUT -> params.replace(arg, args[i + 1]);

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
