package dog.parser;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Enum representing available commands in the Dog application.
 * Provides methods to parse and identify commands from user input.
 */
public enum Command {
    LIST,
    MARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    FIND,
    BYE;

    /**
     * Parses the input string to identify the command.
     *
     * @param input The user input string.
     * @return A <code>Map.Entry</code> containing the matching Command enum (or null if no match), and the suffix.
     */
    public static Entry<Command, String> fromInput(String input) {
        String[] result = input.split(" ", 2);
        assert result.length >= 1 : "Input parsing result must have at least length 1";
        assert result.length <= 2 : "Input parsing result must have at most length 2";

        String inputPrefix = result[0].toLowerCase();

        for (Command cmd : Command.values()) {
            if (inputPrefix.equals(cmd.name().toLowerCase())) {
                if (result.length == 1) {
                    return Map.<Command, String>entry(cmd, "");
                }
                assert result.length >= 2 : "Input parsing result must have at least length 2";
                String inputSuffix = result[1];
                return Map.<Command, String>entry(cmd, inputSuffix);
            }
        }
        return null;
    }
}
