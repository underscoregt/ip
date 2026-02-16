package amia.exception;

/**
 * Centralized error messages for consistent formatting across the application.
 */
public class ErrorMessages {
    // General errors
    public static final String EMPTY_INPUT = "Please provide a command.";
    public static final String UNKNOWN_COMMAND = "I don't understand that command.";

    // Task description errors
    public static final String EMPTY_DESCRIPTION = "The description of a task cannot be empty.";

    // Task list errors
    public static final String INVALID_TASK_NUMBER = "Invalid task number.";
    public static final String TASK_LIST_FULL = "The task list is full (maximum 100 tasks).";
    public static final String NULL_TASK = "Cannot add null task.";

    // Deadline errors
    public static final String EMPTY_DEADLINE = "The deadline cannot be empty.";
    public static final String INVALID_DEADLINE_FORMAT =
            "Invalid format. Use: deadline <description> /by <date>";

    // Event errors
    public static final String EMPTY_START_TIME = "The start time cannot be empty.";
    public static final String EMPTY_END_TIME = "The end time cannot be empty.";
    public static final String INVALID_EVENT_FORMAT =
            "Invalid format. Use: event <description> /from <start> /to <end>";

    // Date/time errors
    public static final String INVALID_DATETIME_FORMAT = "Invalid date/time format. Use: yyyy-MM-dd HHmm";

    // Storage errors
    public static final String CANNOT_LOAD_TASKS = "Unable to load saved tasks from file.";
    public static final String CANNOT_SAVE_TASKS = "Unable to save tasks to file.";

    /**
     * Returns a formatted error message for invalid command format.
     *
     * @param keyword The command keyword (e.g., "mark", "delete").
     * @return Formatted error message.
     */
    public static String invalidIndexFormat(String keyword) {
        return "Invalid format. Use: " + keyword + " <task number>";
    }
}
