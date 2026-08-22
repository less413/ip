package model;

import exceptions.DogException;
import storage.DateUtils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, LocalDate from, LocalDate to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat() {
        return String.format("E | %s | %s | %s | %s", getStatusIcon(), from.toString(), to.toString(), description);
    }

    public static Event fromSaveFormat(String saveString) {
        String[] parts = saveString.split(" \\| ", 5);
        if (parts.length < 5) return null;

        String statusIcon = parts[1].trim();
        String fromStr = parts[2].trim();
        String toStr = parts[3].trim();
        String description = parts[4].trim();
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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateUtils.format(this.from)
                + " to: " + DateUtils.format(this.to) + ")";
    }
}
