package dog;

import dog.exceptions.DogException;
import dog.model.Task;
import dog.model.Deadline;
import dog.model.Event;
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

        switch (command) {
            case BYE:
                STORAGE.save(taskList.getTasks());
                return new CommandResult(FAREWELL, true);
            case LIST:
                return new CommandResult("Here are the tasks in your list:\n" + taskList, false);
            case MARK:
                return new CommandResult(handleMarkTask(rest), false);
            case DELETE:
                return new CommandResult(handleDeleteTask(rest), false);
            case FIND:
                return new CommandResult(handleFindTasks(rest), false);
            case TODO:
                return new CommandResult(handleAddTodo(rest), false);
            case DEADLINE:
                return new CommandResult(handleAddDeadline(rest), false);
            case EVENT:
                return new CommandResult(handleAddEvent(rest), false);
            default:
                return new CommandResult("", false);
        }
    }

    private String handleMarkTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            if (index >= 0 && index < taskList.size()) {
                taskList.markTask(index);
                STORAGE.save(taskList.getTasks());
                return "WOOF! I've marked this task as done:\n " + taskList.getTask(index);
            } else {
                throw new DogException("Task index out of bounds.");
            }
        } catch (NumberFormatException e) {
            throw new DogException("Please provide a valid task number. (e.g., 'mark 2').");
        }
    }

    private String handleDeleteTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            if (index >= 0 && index < taskList.size()) {
                Task deletedTask = taskList.deleteTask(index);
                STORAGE.save(taskList.getTasks());
                return "WOOF! I've deleted this task:\n " + deletedTask +
                        "\nYou have " + taskList.size() + " tasks left in your list! WOOF!";
            } else {
                throw new DogException("Task index out of bounds.");
            }
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
        taskList.addTask(newTodo);
        STORAGE.save(taskList.getTasks());
        return "WOOF! I've added a new task: \n" + newTodo;
    }

    private String handleAddDeadline(String rest) throws DogException {
        Task newDeadline = Deadline.parse(rest);
        taskList.addTask(newDeadline);
        STORAGE.save(taskList.getTasks());
        return "WOOF! I've added a new task: \n" + newDeadline;
    }

    private String handleAddEvent(String rest) throws DogException {
        Task newEvent = Event.parse(rest);
        taskList.addTask(newEvent);
        STORAGE.save(taskList.getTasks());
        return "WOOF! I've added a new task: \n" + newEvent;
    }
}
