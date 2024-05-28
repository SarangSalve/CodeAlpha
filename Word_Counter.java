package Projects;

import java.util.Scanner;

public class Word_Counter {
    
    public static int countWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.trim().split("\\s+");
        return words.length;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string to count the words:");
        String input = scanner.nextLine();

        int wordCount = countWords(input);
        System.out.println("The input string has " + wordCount + " words.");

        scanner.close();
    }
}

