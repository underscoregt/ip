public enum CommandType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    LIST("list"),
    BYE("bye"),
    UNKNOWN("unknown");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    public static CommandType fromString(String input) {
        for (CommandType cmd : CommandType.values()) {
            if (input.startsWith(cmd.value)) {
                return cmd;
            }
        }
        return UNKNOWN;
    }
}
