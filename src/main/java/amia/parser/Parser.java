package amia.parser;

import amia.command.AddDeadlineCommand;
import amia.command.AddEventCommand;
import amia.command.AddTodoCommand;
import amia.command.Command;
import amia.command.DeleteCommand;
import amia.command.ExitCommand;
import amia.command.FindCommand;
import amia.command.ListCommand;
import amia.command.MarkCommand;
import amia.command.UnknownCommand;
import amia.command.UnmarkCommand;
import amia.exception.AmiaException;
import amia.exception.ErrorMessages;

/**
 * Parses user commands and extracts relevant information.
 */
public class Parser {

    /**
     * Parses a command string and returns the appropriate Command object.
     *
     * @param input The user's input string.
     * @return The Command object to execute.
     * @throws AmiaException If parsing fails.
     */
    public static Command parse(String input) throws AmiaException {
        CommandType commandType = parseCommandType(input);
        assert commandType != null : "Command type must be resolved";

        switch (commandType) {
        case TODO:
            return new AddTodoCommand(input);
        case DEADLINE:
            return new AddDeadlineCommand(input);
        case EVENT:
            return new AddEventCommand(input);
        case MARK:
            return new MarkCommand(input);
        case UNMARK:
            return new UnmarkCommand(input);
        case DELETE:
            return new DeleteCommand(input);
        case LIST:
            return new ListCommand();
        case FIND:
            return new FindCommand(input);
        case BYE:
            return new ExitCommand();
        case UNKNOWN:
        default:
            return new UnknownCommand();
        }
    }

    /**
     * Parses a command string and returns the corresponding CommandType.
     *
     * @param input The command string to parse.
     * @return The CommandType corresponding to the input.
     * @throws AmiaException If the input is null, empty, or unknown.
     */
    public static CommandType parseCommandType(String input) throws AmiaException {
        if (input == null || input.trim().isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_INPUT);
        }
        CommandType commandType = CommandType.fromString(input.trim().toLowerCase());
        return commandType;
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
        assert command != null && keyword != null : "Command and keyword must be non-null";
        String description = command.substring(keyword.length()).trim();
        if (description.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_DESCRIPTION);
        }
        return description;
    }

    /**
     * Extracts the index argument from a command by removing the keyword prefix.
     *
     * @param command The full command string.
     * @param keyword The keyword prefix to remove (e.g., "mark", "delete").
     * @return The extracted index argument as a string.
     * @throws AmiaException If the argument is empty.
     */
    public static String extractIndexArgument(String command, String keyword) throws AmiaException {
        assert command != null && keyword != null : "Command and keyword must be non-null";
        String arguments = command.substring(keyword.length()).trim();
        if (arguments.isEmpty()) {
            throw new AmiaException(ErrorMessages.invalidIndexFormat(keyword));
        }
        return arguments;
    }

    /**
     * Parses a string index and converts it to a zero-based integer.
     *
     * @param indexStr The index string to parse.
     * @return The zero-based index as an integer.
     * @throws AmiaException If the index string is not a valid number.
     */
    public static int parseIndex(String indexStr) throws AmiaException {
        assert indexStr != null : "Index string must be non-null";
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new AmiaException(ErrorMessages.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses a deadline command and extracts description and deadline date.
     *
     * @param command The deadline command string.
     * @return A DeadlineInfo object containing the description and deadline.
     * @throws AmiaException If the format is invalid or required fields are empty.
     */
    public static DeadlineInfo parseDeadline(String command) throws AmiaException {
        assert command != null : "Command must be non-null";
        String arguments = command.substring(8).trim();
        int deadlineIndex = arguments.lastIndexOf("/by");
        if (deadlineIndex == -1 || arguments.isEmpty()) {
            throw new AmiaException(ErrorMessages.INVALID_DEADLINE_FORMAT);
        }
        String description = arguments.substring(0, deadlineIndex).trim();
        String deadline = arguments.substring(deadlineIndex + 3).trim();
        if (description.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_DESCRIPTION);
        }
        if (deadline.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_DEADLINE);
        }
        return new DeadlineInfo(description, deadline);
    }

    /**
     * Parses an event command and extracts description, start time, and end time.
     *
     * @param command The event command string.
     * @return An EventInfo object containing the description, start time, and end
     *         time.
     * @throws AmiaException If the format is invalid or required fields are empty.
     */
    public static EventInfo parseEvent(String command) throws AmiaException {
        assert command != null : "Command must be non-null";
        String arguments = command.substring(5).trim();
        int fromIndex = arguments.lastIndexOf("/from");
        int toIndex = arguments.lastIndexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || arguments.isEmpty()) {
            throw new AmiaException(ErrorMessages.INVALID_EVENT_FORMAT);
        }
        String description = arguments.substring(0, fromIndex).trim();
        String from = arguments.substring(fromIndex + 5, toIndex).trim();
        String to = arguments.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_DESCRIPTION);
        }
        if (from.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_START_TIME);
        }
        if (to.isEmpty()) {
            throw new AmiaException(ErrorMessages.EMPTY_END_TIME);
        }
        return new EventInfo(description, from, to);
    }

    /**
     * Inner class to hold deadline information.
     */
    public static class DeadlineInfo {
        private String description;
        private String deadline;

        /**
         * Constructs a DeadlineInfo with the given description and deadline.
         *
         * @param description The task description.
         * @param deadline    The deadline date/time.
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
     * Inner class to hold event information.
     */
    public static class EventInfo {
        private String description;
        private String from;
        private String to;

        /**
         * Constructs an EventInfo with the given description and time range.
         *
         * @param description The task description.
         * @param from        The event start time.
         * @param to          The event end time.
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
