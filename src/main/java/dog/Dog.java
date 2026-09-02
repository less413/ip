package dog;

import dog.exceptions.DogException;
import dog.model.Task;
import dog.model.Deadline;
import dog.model.Event;
import dog.model.TaskList;
import dog.model.Todo;
import dog.parser.Parser;
import dog.storage.Storage;
import dog.ui.Ui;

/**
 * Main logic controller for the Dog task management application.
 * Handles input parsing, storage operations, UI interactions, and task management.
 */
public class Dog {
    private static final Storage STORAGE = new Storage("./data/dog.txt");
    private static final Ui UI = new Ui();
    private TaskList taskList;

    /**
     * Constructs a new Dog instance and loads existing tasks.
     */
    public Dog() {
        this.taskList = new TaskList(STORAGE.load());
    }

    /**
     * Displays the welcome message to the user.
     */
    public void run() {
        UI.showWelcome();
    }

    /**
     * Processes user input and executes the corresponding command.
     *
     * @param input The raw user input string.
     * @return true if the application should continue, false if it should exit.
     */
    public boolean handleInput(String input) {
        try {
            String trimmedInput = input.trim();
            if (trimmedInput.isEmpty()) {
                UI.askForInput();
                return true;
            }

            Parser.Command command = Parser.Command.fromInput(trimmedInput);
            if (command == null) {
                throw new DogException("I don't understand what you're saying :(");
            }

            String rest = command.getCommandRest(trimmedInput);

            switch (command) {
                case BYE:
                    STORAGE.save(taskList.getTasks());
                    UI.showGoodbye();
                    return false;
                case LIST:
                    UI.showMessage("Here are the tasks in your list:");
                    UI.showTaskList(taskList);
                    break;
                case MARK:
                    handleMarkTask(rest);
                    break;
                case DELETE:
                    handleDeleteTask(rest);
                    break;
                case FIND:
                    handleFindTasks(rest);
                    break;
                case TODO:
                    handleAddTodo(rest);
                    break;
                case DEADLINE:
                    handleAddDeadline(rest);
                    break;
                case EVENT:
                    handleAddEvent(rest);
                    break;
                default:
                    break;
            }
        } catch (DogException e) {
            UI.showError(e.getMessage());
            return true;
        } finally {
            UI.showLine();
        }
        return true;
    }

    private void handleMarkTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            if (index >= 0 && index < taskList.size()) {
                taskList.markTask(index);
                UI.showTaskMarked(taskList.getTask(index));
            } else {
                throw new DogException("Task index out of bounds.");
            }
        } catch (NumberFormatException e) {
            throw new DogException("Please provide a valid task number. (e.g., 'mark 2').");
        }
        STORAGE.save(taskList.getTasks());
    }

    private void handleDeleteTask(String rest) throws DogException {
        try {
            int index = Integer.parseInt(rest.trim()) - 1;
            if (index >= 0 && index < taskList.size()) {
                Task deletedTask = taskList.deleteTask(index);
                UI.showTaskDeleted(deletedTask, taskList.size());
            } else {
                throw new DogException("Task index out of bounds.");
            }
        } catch (NumberFormatException e) {
            throw new DogException("Please provide a valid task number. (e.g., 'delete 2').");
        }
        STORAGE.save(taskList.getTasks());
    }

    private void handleFindTasks(String rest) throws DogException {
        TaskList foundTasks = taskList.findTasks(rest);
        UI.showMessage("Here are the matching tasks in your list:");
        UI.showTaskList(foundTasks);
        STORAGE.save(taskList.getTasks());
    }

    private void handleAddTodo(String rest) throws DogException {
        Task newTodo = Todo.parse(rest);
        taskList.addTask(newTodo);
        UI.showTaskAdded(newTodo);
        STORAGE.save(taskList.getTasks());
    }

    private void handleAddDeadline(String rest) throws DogException {
        Task newDeadline = Deadline.parse(rest);
        taskList.addTask(newDeadline);
        UI.showTaskAdded(newDeadline);
        STORAGE.save(taskList.getTasks());
    }

    private void handleAddEvent(String rest) throws DogException {
        Task newEvent = Event.parse(rest);
        taskList.addTask(newEvent);
        UI.showTaskAdded(newEvent);
        STORAGE.save(taskList.getTasks());
    }
}