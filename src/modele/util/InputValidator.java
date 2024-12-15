package modele.util;

import java.util.Scanner;
import java.util.Set;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Repeatedly prompts the user for input until a valid input is received.
     * @param promptMessage The message displayed to the user asking for input.
     * @param validInputs A set of acceptable inputs (e.g., "h", "g", "b", "d", "s").
     * @return A valid input as a string once the user provides one.
     */
    public static String getValidatedInput(String promptMessage, Set<String> validInputs) {
        String input;
        while (true) {
            System.out.println(promptMessage);
            input = scanner.nextLine().trim().toLowerCase();
            if (validInputs.contains(input)) {
                return input; // Valid input, so we return it
            } else {
                System.out.println("Invalid input. Please try again, choose from : "+validInputs);
            }
        }
    }

    /**
     * Overloaded method for cases where valid inputs are a range of integers (like weapon selection).
     * @param promptMessage The message displayed to the user asking for input.
     * @param min The minimum valid integer.
     * @param max The maximum valid integer.
     * @return A valid integer input within the range [min, max].
     */
    public static int getValidatedInput(String promptMessage, int min, int max) {
        int input;
        while (true) {
            System.out.println(promptMessage);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input; // Valid input, so we return it
                } else {
                    System.out.println("Input out of range. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

}
