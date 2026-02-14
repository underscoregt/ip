package amia.parser;

/**
 * Supported command types for user input.
 */
public enum CommandType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    LIST("list"),
    FIND("find"),
    BYE("bye"),
    UNKNOWN("unknown");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    /**
     * Returns the CommandType matching the input prefix.
     *
     * @param input The input string to evaluate.
     * @return The matching CommandType, or UNKNOWN if no match.
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
