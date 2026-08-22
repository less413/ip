import java.util.ArrayList;

/**
 * Manages a list of tasks with operations to add, delete, mark, and retrieve tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Creates a task list with initial tasks.
     * 
     * @param initialTasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> initialTasks) {
        this.tasks = new ArrayList<Task>(initialTasks);
    }

    /**
     * Adds a task to the list.
     * 
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list at the specified index.
     * 
     * @param index the index of the task to delete (0-based)
     * @return the deleted task
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Marks a task as done at the specified index.
     * 
     * @param index the index of the task to mark (0-based)
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Gets the total number of tasks in the list.
     * 
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets a task at the specified index.
     * 
     * @param index the index of the task (0-based)
     * @return the task at the index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Checks if the task list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns an ArrayList copy of the tasks.
     * 
     * @return a copy of the task list
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks);
    }
}