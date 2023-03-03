import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>() {{
            put("-mode", "enc");
            put("-key", "0");
            put("-data", "");
            put("-in", null);
            put("-out", null);
        }};

        for (int i = 0; i < args.length - 1; ++i) {
            switch (args[i]) {
                case "-mode" -> {
                    if (args[i + 1].equals("enc") || args[i + 1].equals("dec")) {
                        params.replace(args[i], args[i + 1]);
                    } else {
                        System.out.println("Wrong mode!\nModes available: enc, dec\n");
                        System.exit(0);
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
                case "-data", "-in", "-out" -> params.replace(args[i], args[i + 1]);
                default -> {
                    if (args[i].charAt(0) == '-') {
                        System.out.println("There is no parameter \"" + args[i] + "\"\n");
                        System.out.println("""
                                                                
                                Here is the list of parameters:
                                -mode : enc, dec
                                -key : integer value;
                                -data : "string prompt"
                                -in : "path/to/file"
                                -out : "path/to/file"
                                                                
                                """);
                    }
                }
            }
        }

        switch (params.get("-mode")) {
            case "enc" -> {
                if (params.get("-in") != null && params.get("-in").length() > 0) {
                    if (params.get("-out") != null && params.get("-out").length() > 0) {
                        encryptStringFromFile(
                                params.get("-in"),
                                params.get("-out"),
                                Integer.parseInt(params.get("-key"))
                        );
                    } else {
                        System.out.println(
                                encryptStringFromFile(
                                        params.get("-in"),
                                        Integer.parseInt(params.get("-key"))
                                )
                        );
                    }
                } else {
                    if (params.get("-out") != null && params.get("-out").length() > 0) {
                        encryptString(
                                params.get("-data"),
                                params.get("-out"),
                                Integer.parseInt(params.get("-key"))
                        );
                    } else {
                        System.out.println(
                                encryptString(
                                        params.get("-data"),
                                        Integer.parseInt(params.get("-key"))
                                )
                        );
                    }
                }
            }

            case "dec" -> {
                if (params.get("-in") != null && params.get("-in").length() > 0) {
                    if (params.get("-out") != null && params.get("-out").length() > 0) {
                        decryptStringFromFile(
                                params.get("-in"),
                                params.get("-out"),
                                Integer.parseInt(params.get("-key"))
                        );
                    } else {
                        System.out.println(
                                decryptStringFromFile(
                                        params.get("-in"),
                                        Integer.parseInt(params.get("-key"))
                                )
                        );
                    }
                } else {
                    if (params.get("-out") != null && params.get("-out").length() > 0) {
                        decryptString(
                                params.get("-data"),
                                params.get("-out"),
                                Integer.parseInt(params.get("-key"))
                        );
                    } else {
                        System.out.println(
                                decryptString(
                                        params.get("-data"),
                                        Integer.parseInt(params.get("-key"))
                                )
                        );
                    }
                }
            }
        }
    }

    public static String encryptString(String prompt, int key) {
        return prompt.codePoints()
                .map(c -> (char) (c + key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    public static void encryptString(String data, String outPath, int key) {
        String encrypted = encryptStringFromFile(data, key);

        try (FileWriter fileWriter = new FileWriter(outPath)) {
            fileWriter.write(encrypted);
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static String encryptStringFromFile(String inPath, int key) {
        String prompt = null;

        try {
            prompt = new String(Files.readAllBytes(Paths.get(inPath)));
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }

        System.out.println(prompt);

        return encryptString(prompt, key);
    }

    public static void encryptStringFromFile(String inPath, String outPath, int key) {
        String encrypted = encryptStringFromFile(inPath, key);

        try (FileWriter fileWriter = new FileWriter(outPath)) {
            fileWriter.write(encrypted);
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }

        System.out.println(encrypted);
    }

    public static String decryptString(String prompt, int key) {
        return prompt.codePoints()
                .map(c -> (char) (c - key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    public static void decryptString(String data, String outPath, int key) {
        String encrypted = decryptStringFromFile(data, key);

        try (FileWriter fileWriter = new FileWriter(outPath)) {
            fileWriter.write(encrypted);
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static String decryptStringFromFile(String inPath, int key) {
        String prompt = null;

        try {
            prompt = new String(Files.readAllBytes(Paths.get(inPath)));
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }

        return decryptString(prompt, key);
    }

    public static void decryptStringFromFile(String inPath, String outPath, int key) {
        String decrypted = decryptStringFromFile(inPath, key);

        try (FileWriter fileWriter = new FileWriter(outPath)) {
            fileWriter.write(decrypted);
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(0);
        }
    }
}
