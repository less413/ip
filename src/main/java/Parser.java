import java.util.Scanner;

public class Parser {
    /**
     * Enum representing available commands in the Dog application.
     * Provides methods to identify commands from user input.
     */
    public enum Command {
        LIST,
        MARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT,
        BYE;

        /**
         * Parses the input string to identify the command.
         * 
         * @param input The user input string
         * @return The matching Command enum value, or null if no match is found
         */
        public static Command fromInput(String input) {
            String lowerInput = input.toLowerCase().trim();
            for (Command cmd : Command.values()) {
                if (cmd.name().toLowerCase().equals(lowerInput)) {
                    return cmd;
                }
                if (lowerInput.startsWith(cmd.name().toLowerCase())) {
                    return cmd;
                }
            }
            return null;
        }

        /**
         * Extracts the rest of the input after the command name.
         * 
         * @param input The original user input string
         * @return The portion of input after the command name, or empty string if no match
         */
        public String getCommandRest(String input) {
            if (!input.startsWith(this.name().toLowerCase())) {
                return "";
            }
            return input.substring(this.name().length());
        }
    }
}
