package storage;

import model.Deadline;
import model.Event;
import model.Task;
import model.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a file storage.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath the path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads a task list from the storage file.
     *
     * @return an ArrayList of loaded tasks, or an empty list if the file doesn't exist.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks: File not found");
        }
        return tasks;
    }

    /**
     * Saves a task list to the storage file.
     *
     * @param tasks the list of tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            java.io.BufferedWriter writer = new java.io.BufferedWriter(fileWriter);
            for (Task task : tasks) {
                writer.write(task.toSaveFormat());
                writer.newLine();
            }
            writer.close();
        } catch (java.io.IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Parses a task from a line of the storage file.
     *
     * @param line the line to parse.
     * @return the parsed Task, or null if the line format is invalid.
     */
    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ", 2);
            String type = parts[0].trim();

            switch (type) {
                case "T":
                    return Todo.fromSaveFormat(line);
                case "D":
                    return Deadline.fromSaveFormat(line);
                case "E":
                    return Event.fromSaveFormat(line);
                default:
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error parsing task line: " + line);
            return null;
        }
    }
}