package dog;

import java.util.Scanner;

import dog.exceptions.DogException;
import dog.ui.Ui;

/**
 * Main entry point for the Dog task management application.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Dog dog = new Dog();
    private static final Ui ui = new Ui();

    /**
     * Orchestrates between user input, Dog processing, and UI display.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        ui.showDogMessage(dog.getWelcomeString());

        while (SCANNER.hasNextLine()) {
            String input = SCANNER.nextLine();
            try {
                Dog.CommandResult result = dog.processInput(input);
                ui.showDogMessage(result.getMessage());

                if (result.shouldExit()) {
                    break;
                }
            } catch (DogException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }

        SCANNER.close();
    }
}
