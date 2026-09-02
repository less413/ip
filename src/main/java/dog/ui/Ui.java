package dog.ui;

/**
 * Handles user interface interactions.
 */
public class Ui {
    public static final String LINE = "____________________________________________________________";

    /**
     * Displays a message to the user.
     *
     * @param message the message to display.
     */
    public void showDogMessage(String message) {
        System.out.println(message);
        System.out.println(LINE);
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display.
     */
    public void showErrorMessage(String message) {
        showDogMessage("WOOF WOOF there is something wrong:\n" + message);
    }
}
