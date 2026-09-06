package dog;

import dog.exceptions.DogException;
import dog.model.Deadline;
import dog.model.Event;
import dog.model.Task;
import dog.model.TaskList;
import dog.model.Todo;
import dog.parser.Parser;
import dog.storage.Storage;

/**
 * Main logic controller for the Dog task management application.
 * Handles input parsing, storage operations, and task management.
 * Returns response strings that Main passes to Ui for display.
 */
public class Dog {
    private static final Storage STORAGE = new Storage("./data/dog.txt");

    private static final String BANNER = "      _____\n"
            + "      |  __ \\  ____   ___ _ \n"
            + "      | |  | |/ __ \\ / __' |\n"
            + "      | |  | | |  | | |__| |\n"
            + "      | |__| | |__| |\\___  |\n"
            + "      |_____/ \\____/ ____/ |\n"
            + "                     \\____/ \n";
    private static final String GREETING = "WOOF WOOF! How can I help? WOOF";
    private static final String FAREWELL = "WOOF! Goodbye! WOOF WOOF";

    private TaskList taskList;

    /**
     * Constructs a new Dog instance and loads existing tasks.
     */
    public Dog() {
        this.taskList = new TaskList(STORAGE.load());
    }

    public String getWelcomeString() {
        return BANNER + "\n" + GREETING;
    }

    /**
     * Represents the result of processing a user input.
     */
    public static class CommandResult {
        private final String message;
        private final boolean shouldExit;

        /**
         * Constructs a new CommandResult instance.
         */
        public CommandResult(String message, boolean shouldExit) {
            this.message = message;
            this.shouldExit = shouldExit;
        }

        public String getMessage() {
            return message;
        }

        public boolean shouldExit() {
            return shouldExit;
        }
    }

    /**
     * Processes user input and returns the response message.
     *
     * @param input The raw user input string.
     * @return The command result containing message and exit status.
     * @throws DogException if there's an error processing the input.
     */
    public CommandResult processInput(String input) throws DogException {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return new CommandResult("...say something? woof...", false);
        }

        Parser.Command command = Parser.Command.fromInput(trimmedInput);
        if (command == null) {
            throw new DogException("I don't understand what you're saying :(");
        }

        String rest = command.getCommandRest(trimmedInput);
        String reply;

        switch (command) {
            case BYE:
                STORAGE.save(taskList.getTasks());
                return new CommandResult(FAREWELL, true);
            case LIST:
                reply = handlePrintList();
                return new CommandResult(reply, false);
            case MARK:
                reply = handleMarkTask(rest);
                return new CommandResult(reply, false);
            case DELETE:
                reply = handleDeleteTask(rest);
                return new CommandResult(reply, false);
            case FIND:
                reply = handleFindTasks(rest);
                return new CommandResult(reply, false);
            case TODO:
                reply = handleAddTodo(rest);
                return new CommandResult(reply, false);
            case DEADLINE:
                reply = handleAddDeadline(rest);
                return new CommandResult(reply, false);
            case EVENT:
                reply = handleAddEvent(rest);
                return new CommandResult(reply, false);
            default:
                assert false : "Unexpected command state";
                return new CommandResult("", false);
        }
    }

    private String handlePrintList() {
        return "Here are the tasks in your list:\n" + taskList;
    }

    private String handleMarkTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            taskList.markTask(index);
            STORAGE.save(taskList.getTasks());
            return "WOOF! I've marked this task as done:\n " + taskList.getTask(index);
        } catch (NumberFormatException e) {
            throw new DogException("Please provide a valid task number. (e.g., 'mark 2').");
        }
    }

    private String handleDeleteTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            Task deletedTask = taskList.deleteTask(index);
            STORAGE.save(taskList.getTasks());
            return "WOOF! I've deleted this task:\n " + deletedTask
                    + "\nYou have " + taskList.size() + " tasks left in your list! WOOF!";
        } catch (NumberFormatException e) {
            throw new DogException("Please provide a valid task number. (e.g., 'delete 2').");
        }
    }

    private String handleFindTasks(String rest) throws DogException {
        TaskList foundTasks = taskList.findTasks(rest);
        STORAGE.save(taskList.getTasks());
        return "Here are the matching tasks in your list:\n" + foundTasks;
    }

    private String handleAddTodo(String rest) throws DogException {
        Task newTodo = Todo.parse(rest);
        assert newTodo != null : "parsed Todo should not be null";
        return handleAddTask(newTodo);
    }

    private String handleAddDeadline(String rest) throws DogException {
        Task newDeadline = Deadline.parse(rest);
        assert newDeadline != null : "parsed Deadline should not be null";
        return handleAddTask(newDeadline);
    }

    private String handleAddEvent(String rest) throws DogException {
        Task newEvent = Event.parse(rest);
        assert newEvent != null : "parsed Event should not be null";
        return handleAddTask(newEvent);
    }

    private String handleAddTask(Task task) {
        taskList.addTask(task);
        STORAGE.save(taskList.getTasks());
        return "WOOF! I've added a new task: \n" + task;
    }
}
