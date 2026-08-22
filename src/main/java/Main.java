import exceptions.DogException;
import model.Task;
import model.TaskList;
import model.Todo;
import model.Deadline;
import model.Event;
import parser.Parser;
import storage.Storage;
import ui.Ui;
import java.util.Scanner;

public class Main {
    private static final Storage storage = new Storage("./data/dog.txt");
    private static final Ui ui = new Ui();
    private static TaskList taskList = new TaskList();

    public static void main(String[] args) {
        ui.showWelcome();
        taskList = new TaskList(storage.load());
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    ui.askForInput();
                } else {
                    Parser.Command command = Parser.Command.fromInput(input);
                    if (command == null) {
                        throw new DogException("I don't understand what you're saying :(");
                    }

                    String rest = command.getCommandRest(input);

                    switch (command) {
                        case BYE:
                            storage.save(taskList.getTasks());
                            ui.showGoodbye();
                            scanner.close();
                            return;
                        case LIST:
                            ui.showMessage("Here are the tasks in your list:");
                            ui.showTaskList(taskList.getTasks());
                            break;
                        case MARK:
                            try {
                                int index = Integer.parseInt(rest.trim()) - 1;
                                if (index >= 0 && index < taskList.size()) {
                                    taskList.markTask(index);
                                    ui.showTaskMarked(taskList.getTask(index));
                                } else {
                                    throw new DogException("Task index out of bounds.");
                                }
                            } catch (NumberFormatException e) {
                                throw new DogException("Please provide a valid task number. (e.g., 'mark 2').");
                            }
                            storage.save(taskList.getTasks());
                            break;
                        case DELETE:
                            try {
                                int index = Integer.parseInt(rest.trim()) - 1;
                                if (index >= 0 && index < taskList.size()) {
                                    Task deletedTask = taskList.deleteTask(index);
                                    ui.showTaskDeleted(deletedTask, taskList.size());
                                } else {
                                    throw new DogException("Task index out of bounds.");
                                }
                            } catch (NumberFormatException e) {
                                throw new DogException("Please provide a valid task number. (e.g., 'delete 2').");
                            }
                            storage.save(taskList.getTasks());
                            break;
                        case TODO:
                            Task newTodo = Todo.parse(rest);
                            taskList.addTask(newTodo);
                            ui.showTaskAdded(newTodo);
                            storage.save(taskList.getTasks());
                            break;
                        case DEADLINE:
                            Task newDeadline = Deadline.parse(rest);
                            taskList.addTask(newDeadline);
                            ui.showTaskAdded(newDeadline);
                            storage.save(taskList.getTasks());
                            break;
                        case EVENT:
                            Task newEvent = Event.parse(rest);
                            taskList.addTask(newEvent);
                            ui.showTaskAdded(newEvent);
                            storage.save(taskList.getTasks());
                            break;
                    }
                }
            } catch (DogException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
}