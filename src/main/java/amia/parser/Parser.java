package amia.parser;

import amia.exception.AmiaException;

/**
 * Parses user commands and extracts relevant information.
 */
public class Parser {

    /**
     * Parses a command string and returns the corresponding CommandType.
     *
     * @param input The command string to parse.
     * @return The CommandType corresponding to the input.
     * @throws AmiaException If the input is null, empty, or unknown.
     */
    public static CommandType parseCommandType(String input) throws AmiaException {
        if (input == null || input.trim().isEmpty()) {
            throw new AmiaException("...?");
        }
        CommandType cmdType = CommandType.fromString(input.trim().toLowerCase());
        return cmdType;
    }

    /**
     * Extracts the description from a command by removing the keyword prefix.
     *
     * @param command The full command string.
     * @param keyword The keyword prefix to remove (e.g., "todo", "deadline").
     * @return The extracted description.
     * @throws AmiaException If the description is empty.
     */
    public static String extractDescription(String command, String keyword) throws AmiaException {
        String desc = command.substring(keyword.length()).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("... The description of a task cannot be empty...");
        }
        return desc;
    }

    /**
     * Extracts the index argument from a command by removing the keyword prefix.
     *
     * @param command The full command string.
     * @param keyword The keyword prefix to remove (e.g., "mark", "delete").
     * @return The extracted index argument as a string.
     * @throws AmiaException If the argument is empty.
     */
    public static String extractIndexArg(String command, String keyword) throws AmiaException {
        String args = command.substring(keyword.length()).trim();
        if (args.isEmpty()) {
            throw new AmiaException("... Invalid format... Use: " + keyword + " <index>");
        }
        return args;
    }

    /**
     * Parses a 1-based index string into a 0-based index.
     *
     * @param indexStr The index string provided by the user.
     * @return The 0-based index.
     * @throws AmiaException If the index is not a valid number.
     */
    public static int parseIndex(String indexStr) throws AmiaException {
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new AmiaException("... Invalid task number...");
        }
    }

    /**
     * Parses a deadline command into its description and deadline.
     *
     * @param command The full deadline command string.
     * @return A DeadlineInfo object containing the parsed values.
     * @throws AmiaException If the command format is invalid.
     */
    public static DeadlineInfo parseDeadline(String command) throws AmiaException {
        String args = command.substring(8).trim();
        int byIdx = args.lastIndexOf("/by");
        if (byIdx == -1 || args.isEmpty()) {
            throw new AmiaException("Invalid format... Use: deadline <desc> /by <date>");
        }
        String desc = args.substring(0, byIdx).trim();
        String by = args.substring(byIdx + 3).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("... The description of a task can't be empty...");
        }
        if (by.isEmpty()) {
            throw new AmiaException("... The deadline can't be empty...");
        }
        return new DeadlineInfo(desc, by);
    }

    /**
     * Parses an event command into its description, start time, and end time.
     *
     * @param command The full event command string.
     * @return An EventInfo object containing the parsed values.
     * @throws AmiaException If the command format is invalid.
     */
    public static EventInfo parseEvent(String command) throws AmiaException {
        String args = command.substring(5).trim();
        int fromIdx = args.lastIndexOf("/from");
        int toIdx = args.lastIndexOf("/to");
        if (fromIdx == -1 || toIdx == -1 || args.isEmpty()) {
            throw new AmiaException("Invalid format... Use: event <desc> /from <start> /to <end>");
        }
        String desc = args.substring(0, fromIdx).trim();
        String from = args.substring(fromIdx + 5, toIdx).trim();
        String to = args.substring(toIdx + 3).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("...The description of a task can't be empty...");
        }
        if (from.isEmpty()) {
            throw new AmiaException("... The start time can't be empty...");
        }
        if (to.isEmpty()) {
            throw new AmiaException("... The end time can't be empty...");
        }
        return new EventInfo(desc, from, to);
    }

    /**
     * Container for deadline parsing results.
     */
    public static class DeadlineInfo {
        private String description;
        private String deadline;

        /**
         * Constructs a DeadlineInfo object.
         *
         * @param description The task description.
         * @param deadline The deadline string.
         */
        public DeadlineInfo(String description, String deadline) {
            this.description = description;
            this.deadline = deadline;
        }

        public String getDescription() {
            return description;
        }

        public String getDeadline() {
            return deadline;
        }
    }

    /**
     * Container for event parsing results.
     */
    public static class EventInfo {
        private String description;
        private String from;
        private String to;

        /**
         * Constructs an EventInfo object.
         *
         * @param description The task description.
         * @param from The start time string.
         * @param to The end time string.
         */
        public EventInfo(String description, String from, String to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }

        public String getDescription() {
            return description;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }
}
