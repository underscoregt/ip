package amia;

import amia.command.Command;
import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Main application class for the Amia task manager. Handles the main
 * application loop and task management operations.
 */
public class Amia {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private boolean isExit = false;

    /**
     * Constructs an Amia instance with default file path. Initializes storage, UI,
     * and loads saved tasks.
     */
    public Amia() {
        this(null);
    }

    /**
     * Constructs an Amia instance for testing with custom file path.
     *
     * @param filePath The path to the data file.
     */
    public Amia(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.tasks = loadTasks();
        assert this.storage != null && this.ui != null : "Storage and UI must be initialized";
        assert this.tasks != null : "Tasks must be initialized";
    }

    /**
     * Loads tasks from storage, returning an empty TaskList if loading fails.
     *
     * @return The loaded TaskList or a new empty TaskList.
     */
    private TaskList loadTasks() {
        try {
            return new TaskList(storage.load());
        } catch (AmiaException e) {
            return new TaskList();
        }
    }

    /**
     * Starts the application and runs the main loop.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        Amia amia = new Amia();
        amia.start();
        amia.loop();
        amia.exit();
    }

    /**
     * Generates a response for the user's input. Used by the GUI.
     *
     * @param input The user's command.
     * @return The response string to display.
     */
    public String getResponse(String input) {
        try {
            return executeCommand(input);
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }

    /**
     * Checks if the last command was an exit command.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean shouldExit() {
        return isExit;
    }

    /**
     * Continuously reads user commands and processes them until the user exits.
     * Used by the CLI.
     */
    public void loop() {
        while (!shouldExit()) {
            processOneCommand();
        }
        ui.close();
    }

    /**
     * Processes a single command from the user in the CLI. Reads input, executes
     * the command, and displays the result.
     */
    private void processOneCommand() {
        try {
            String input = ui.readCommand();
            String response = getResponse(input);
            displayResponse(response);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Executes a parsed command and updates exit state.
     *
     * @param input The user's command string.
     * @return The response string from command execution.
     * @throws AmiaException If parsing or execution fails.
     */
    private String executeCommand(String input) throws AmiaException {
        Command command = Parser.parse(input);
        String response = command.execute(tasks, ui, storage);
        isExit = command.isExit();
        return response;
    }

    /**
     * Displays a response message with formatting lines.
     *
     * @param response The message to display.
     */
    private void displayResponse(String response) {
        ui.showLine();
        ui.showMessage(response);
        ui.showLine();
    }

    /**
     * Displays an error message with formatting lines.
     *
     * @param errorMessage The error message to display.
     */
    private void displayError(String errorMessage) {
        ui.showLine();
        ui.showMessage(errorMessage);
        ui.showLine();
    }

    /**
     * Initializes the application by displaying a welcome message.
     */
    public void start() {
        ui.showLine();
        ui.showMessage("Hello! I'm Amia!");
        ui.showMessage("What can I do for you?");
        ui.showLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void exit() {
        ui.showGoodbye();
    }
}
