package dog;

import java.util.Scanner;

/**
 * Main entry point for the Dog task management application.
 * Handles only the scanning of commands from user input.
 * Delegates all processing to the Dog class.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Main method that starts the Dog application.
     * Initializes the Dog instance and passes user input to it.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.run();

        while (SCANNER.hasNextLine()) {
            String input = SCANNER.nextLine();
            if (!dog.handleInput(input)) {
                break; // Exit loop when BYE received
            }
        }

        SCANNER.close();
    }
}