package dog.ui;

import dog.model.Task;
import dog.model.TaskList;

/**
 * Handles user interface interactions - printing messages to the console.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER = "      _____\n"
            + "      |  __ \\  ____   ___ _ \n"
            + "      | |  | |/ __ \\ / __' |\n"
            + "      | |  | | |  | | |__| |\n"
            + "      | |__| | |__| |\\___  |\n"
            + "      |_____/ \\____/ ____/ |\n"
            + "                     \\____/ \n";
    private static final String GREETING = "WOOF WOOF! How can I help? WOOF";
    private static final String FAREWELL = "WOOF! Goodbye! WOOF WOOF";

    /**
     * Displays a message to the user.
     *
     * @param message the message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the welcome banner and greeting.
     */
    public void showWelcome() {
        showMessage(BANNER);
        showMessage(GREETING);
        showMessage(LINE);
    }

    /**
     * Displays a farewell message.
     */
    public void showGoodbye() {
        showMessage(FAREWELL);
    }

    /**
     * Displays a separator line.
     */
    public void showLine() {
        showMessage(LINE);
    }

    /**
     * Displays an error message with a dog-themed prefix.
     *
     * @param message the error message to display.
     */
    public void showError(String message) {
        showMessage("WOOF WOOF there is something wrong:\n" + message);
    }

    /**
     * Displays the tasks in a given task list.
     *
     * @param taskList the list of tasks to display.
     */
    public void showTaskList(TaskList taskList) {
        showMessage(taskList.toString());
    }

    /**
     * Displays a task that was marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        showMessage("WOOF! I've marked this task as done:");
        showMessage(" " + task);
    }

    /**
     * Displays a task that was deleted.
     *
     * @param task the task that was deleted.
     * @param remainingCount the number of tasks remaining.
     */
    public void showTaskDeleted(Task task, int remainingCount) {
        showMessage("WOOF! I've deleted this task:");
        showMessage(" " + task);
        showMessage("You have " + remainingCount + " tasks left in your list! WOOF!");
    }

    /**
     * Displays a task that was added.
     *
     * @param task the newly-added task.
     */
    public void showTaskAdded(Task task) {
        showMessage("WOOF! I've added a new task: \n" + task);
    }

    /**
     * Displays a message prompting the user to provide a non-empty input.
     */
    public void askForInput() {
        showMessage("...say something? woof...");
    }
}
