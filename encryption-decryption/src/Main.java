import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>() {{
            put("-mode", "enc");
            put("-key", "0");
            put("-data", "");
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
                case "-data" -> params.replace(args[i], args[i + 1]);
            }
        }

        if (params.get("-mode").equals("enc")) {
            System.out.println(encryptString(params.get("-data"), Integer.parseInt(params.get("-key"))));
        } else {
            System.out.println(decryptString(params.get("-data"), Integer.parseInt(params.get("-key"))));
        }
    }

    private static String encryptString(String prompt, int key) {
        return prompt.codePoints()
                .map(c -> (char) (c + key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private static String decryptString(String prompt, int key) {
        return prompt.codePoints()
                .map(c -> (char) (c - key))
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
