package model;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.DogException;
import storage.DateUtils;

/**
 * Represents a deadline task that must be completed by a specific date.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Creates a deadline task with the specified description and deadline date.
     *
     * @param description the task description.
     * @param by          the deadline date.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a deadline task with the specified description, deadline date, and completion status.
     *
     * @param description the task description.
     * @param by          the deadline date.
     * @param isDone      true if the task is completed, false otherwise.
     */
    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Parses a deadline task from its saved string format.
     *
     * @param saveString the saved string in format "D | [X or space] | date | description".
     * @return the parsed Deadline, or null if the format is invalid.
     */
    public static Deadline fromSaveFormat(String saveString) {
        if (saveString == null) return null;

        String[] parts = saveString.split(" \\| ", 4);
        if (parts.length < 4) return null;

        String taskIcon = parts[0];
        String statusIcon = parts[1];
        String byStr = parts[2];
        String description = parts[3];

        // Return null if task/status icon is invalid
        if (!taskIcon.equals("D") || !statusIcon.equals("X") && !statusIcon.equals(" ")) {
            return null;
        }

        boolean isDone = statusIcon.equals("X");

        try {
            LocalDate by = DateUtils.parse(byStr);
            return new Deadline(description, by, isDone);
        } catch (DogException e) {
            System.out.println("Error loading deadline: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses a deadline task from user input.
     *
     * @param input the user input string.
     * @return the parsed Deadline.
     * @throws DogException if the input format is invalid.
     */
    public static Deadline parse(String input) throws DogException {
        String BAD_INPUT_MSG = "Deadline tasks must have a description and deadline!\n"
                + "Expected: deadline <description> /by <deadline>";

        // Pattern: " <description> /by <by>" where <by> is in yyyy-MM-dd format
        Pattern pattern = Pattern.compile("^\\s+(.+?)\\s+/by\\s+(.+?)\\s*$");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new DogException(BAD_INPUT_MSG);
        }

        String description = matcher.group(1).trim();
        String byStr = matcher.group(2).trim();

        if (description.isEmpty() || byStr.isEmpty()) {
            throw new DogException(BAD_INPUT_MSG);
        }

        LocalDate by = DateUtils.parse(byStr);
        return new Deadline(description, by);
    }

    /**
     * Returns a string representation of the deadline task for saving to file.
     *
     * @return the deadline in save format.
     */
    @Override
    public String toSaveFormat() {
        return String.format("D | %s | %s | %s", getStatusIcon(), by.toString(), description);
    }

    /**
     * Returns a string representation of the deadline task for display.
     *
     * @return the deadline in display format.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateUtils.format(this.by) + ")";
    }
}