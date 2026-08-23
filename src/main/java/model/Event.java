package model;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.DogException;
import storage.DateUtils;

/**
 * Represents an event task that occurs within a specific time period.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates an event task with the specified description and date range.
     *
     * @param description the task description.
     * @param from        the start date of the event.
     * @param to          the end date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an event task with the specified description, date range, and completion status.
     *
     * @param description the task description.
     * @param from        the start date of the event.
     * @param to          the end date of the event.
     * @param isDone      true if the task is completed, false otherwise.
     */
    public Event(String description, LocalDate from, LocalDate to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Parses an event task from its saved string format.
     *
     * @param saveString the saved string in format "E | [X or space] | from | to | description".
     * @return the parsed Event, or null if the format is invalid.
     */
    public static Event fromSaveFormat(String saveString) {
        if (saveString == null) return null;

        String[] parts = saveString.split(" \\| ", 5);
        if (parts.length < 5) return null;

        String taskIcon = parts[0];
        String statusIcon = parts[1];
        String fromStr = parts[2];
        String toStr = parts[3];
        String description = parts[4];

        // Return null if task/status icon is invalid
        if (!taskIcon.equals("E") || !statusIcon.equals("X") && !statusIcon.equals(" ")) {
            return null;
        }

        boolean isDone = statusIcon.equals("X");

        try {
            LocalDate from = DateUtils.parse(fromStr);
            LocalDate to = DateUtils.parse(toStr);
            return new Event(description, from, to, isDone);
        } catch (DogException e) {
            System.out.println("Error loading event: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses an event task from user input.
     *
     * @param input the user input string.
     * @return the parsed Event.
     * @throws DogException if the input format is invalid.
     */
    public static Event parse(String input) throws DogException {
        String BAD_INPUT_MSG = "Event tasks must have a description, a start date, and an end date!\n"
                + "Expected: event <description> /from <start date> /to <end date>";

        // Pattern: " <description> /from <from> /to <to>"
        Pattern pattern = Pattern.compile("^\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+?)\\s*$");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new DogException(BAD_INPUT_MSG);
        }

        String description = matcher.group(1).trim();
        String fromStr = matcher.group(2).trim();
        String toStr = matcher.group(3).trim();

        if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            throw new DogException(BAD_INPUT_MSG);
        }

        LocalDate from = DateUtils.parse(fromStr);
        LocalDate to = DateUtils.parse(toStr);
        return new Event(description, from, to);
    }

    /**
     * Returns a string representation of the event task for saving to file.
     *
     * @return the event in save format.
     */
    @Override
    public String toSaveFormat() {
        return String.format("E | %s | %s | %s | %s", getStatusIcon(), from.toString(), to.toString(), description);
    }

    /**
     * Returns a string representation of the event task for display.
     *
     * @return the event in display format.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateUtils.format(this.from)
                + " to: " + DateUtils.format(this.to) + ")";
    }
}