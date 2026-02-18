package amia.parser;

/**
 * Enum representing different types of commands that can be parsed.
 */
public enum CommandType {
    TODO("todo"), DEADLINE("deadline"), EVENT("event"), MARK("mark"), UNMARK("unmark"), DELETE("delete"), LIST("list"),
    FIND("find"), BYE("bye"), UNKNOWN("unknown");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    /**
     * Converts a string to the corresponding CommandType.
     *
     * @param input The input string to convert.
     * @return The CommandType that matches the input, or UNKNOWN if no match.
     */
    public static CommandType fromString(String input) {
        // Extract the first word from the input to avoid false matches
        String[] parts = input.split("\\s+", 2);
        String command = parts[0].toLowerCase();

        for (CommandType cmd : CommandType.values()) {
            if (command.equals(cmd.value)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }
}
