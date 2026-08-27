import java.util.Scanner;

import exceptions.DogException;
import model.Deadline;
import model.Event;
import model.Task;
import model.TaskList;
import model.Todo;
import parser.Parser;
import storage.Storage;
import ui.Ui;

/**
 * Main entry point for the Dog task management application.
 * Initializes the application components and handles the main interaction loop.
 */
public class Main {
    private static final Storage STORAGE = new Storage("./data/dog.txt");
    private static final Ui UI = new Ui();

    /**
     * Main method that starts the Dog application.
     * Shows welcome message, loads tasks, and processes user commands until exit.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        UI.showWelcome();
        TaskList taskList = new TaskList(STORAGE.load());
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    UI.askForInput();
                } else {
                    Parser.Command command = Parser.Command.fromInput(input);
                    if (command == null) {
                        throw new DogException("I don't understand what you're saying :(");
                    }

                    String rest = command.getCommandRest(input);

                    switch (command) {
                        case BYE:
                            STORAGE.save(taskList.getTasks());
                            UI.showGoodbye();
                            scanner.close();
                            return;
                        case LIST:
                            UI.showMessage("Here are the tasks in your list:");
                            UI.showTaskList(taskList);
                            break;
                        case MARK:
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
                            break;
                        case DELETE:
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
                            break;
                        case FIND:
                            TaskList foundTasks = taskList.findTasks(rest);
                            UI.showMessage("Here are the matching tasks in your list:");
                            UI.showTaskList(foundTasks);
                            STORAGE.save(taskList.getTasks());
                            break;
                        case TODO:
                            Task newTodo = Todo.parse(rest);
                            taskList.addTask(newTodo);
                            UI.showTaskAdded(newTodo);
                            STORAGE.save(taskList.getTasks());
                            break;
                        case DEADLINE:
                            Task newDeadline = Deadline.parse(rest);
                            taskList.addTask(newDeadline);
                            UI.showTaskAdded(newDeadline);
                            STORAGE.save(taskList.getTasks());
                            break;
                        case EVENT:
                            Task newEvent = Event.parse(rest);
                            taskList.addTask(newEvent);
                            UI.showTaskAdded(newEvent);
                            STORAGE.save(taskList.getTasks());
                            break;
                    }
                }
            } catch (DogException e) {
                UI.showError(e.getMessage());
            } finally {
                UI.showLine();
            }
        }
    }
}