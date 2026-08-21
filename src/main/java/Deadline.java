import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toSaveFormat() {
        return String.format("D | %s | %s | %s", getStatusIcon(), by.toString(), description);
    }

    public static Deadline fromSaveFormat(String saveString) {
        String[] parts = saveString.split(" \\| ", 4);
        if (parts.length < 4) return null;

        String statusIcon = parts[1].trim();
        String byStr = parts[2].trim();
        String description = parts[3].trim();
        boolean isDone = statusIcon.equals("X");

        try {
            LocalDate by = DateUtils.parse(byStr);
            return new Deadline(description, by, isDone);
        } catch (DogException e) {
            System.out.println("Error loading deadline: " + e.getMessage());
            return null;
        }
    }

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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateUtils.format(this.by) + ")";
    }
}
