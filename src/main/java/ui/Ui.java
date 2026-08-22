package ui;

import model.Task;

import java.util.ArrayList;

/**
 * Handles user interface interactions - printing messages to the console.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER =
            "      _____                \n"
          + "      |  __ \\  ____   ___ _ \n"
          + "      | |  | |/ __ \\ / __' |\n"
          + "      | |  | | |  | | |__| |\n"
          + "      | |__| | |__| |\\___  |\n"
          + "      |_____/ \\____/ ____/ |\n"
          + "                     \\____/ \n";
    private static final String GREETING = "WOOF WOOF How can I help? WOOF";
    private static final String FAREWELL = "WOOF Goodbye! WOOF WOOF";

    /**
     * Displays the welcome banner and greeting.
     */
    public void showWelcome() {
        System.out.println(BANNER);
        System.out.println(GREETING);
        System.out.println(LINE);
    }

    /**
     * Displays a farewell message.
     */
    public void showGoodbye() {
        System.out.println(FAREWELL);
    }

    /**
     * Displays a message to the user.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a message to the user.
     * 
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message.
     * 
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("WOOF WOOF there is something wrong:\n" + message);
    }

    /**
     * Displays a list of tasks.
     * 
     * @param tasks the list of tasks to display
     */
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            showMessage(" (Your list is currently empty)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                showMessage((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays a task that was marked as done.
     * 
     * @param task the task that was marked
     */
    public void showTaskMarked(Task task) {
        showMessage("WOOF! I've marked this task as done:");
        showMessage(" " + task);
    }

    /**
     * Displays a task that was deleted.
     * 
     * @param task the task that was deleted
     * @param remainingCount the number of tasks remaining
     */
    public void showTaskDeleted(Task task, int remainingCount) {
        showMessage("WOOF! I've deleted this task:");
        showMessage(" " + task);
        showMessage("You have " + remainingCount + " tasks left in your list! WOOF!");
    }

    /**
     * Displays a task that was added.
     * 
     * @param task the task that was added
     */
    public void showTaskAdded(Task task) {
        showMessage("WOOF! I've added a new task: \n" + task);
    }

    /**
     * Displays a message asking the user to say something.
     */
    public void askForInput() {
        System.out.println("...say something? woof...");
    }
}