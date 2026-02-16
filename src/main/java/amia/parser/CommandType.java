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
        for (CommandType cmd : CommandType.values()) {
            if (input.startsWith(cmd.value)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }
}
