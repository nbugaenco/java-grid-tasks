import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String action = scanner.nextLine();

        switch (action) {
            case "enc" -> System.out.println(encryptString(scanner.nextLine(), scanner.nextInt()));
            case "dec" -> System.out.println(decryptString(scanner.nextLine(), scanner.nextInt()));
            default -> System.out.println("Wrong action!");
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
