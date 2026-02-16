package amia;

import amia.command.AddCommand;
import amia.command.Command;
import amia.command.DeleteCommand;
import amia.command.ExitCommand;
import amia.command.FindCommand;
import amia.command.ListCommand;
import amia.command.MarkCommand;
import amia.command.UnknownCommand;
import amia.command.UnmarkCommand;
import amia.exception.AmiaException;
import amia.parser.CommandType;
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
        this.storage = new Storage(null);
        this.ui = new Ui();
        assert this.storage != null && this.ui != null : "Storage and UI must be initialized";
        try {
            this.tasks = new TaskList(storage.load());
        } catch (AmiaException e) {
            this.tasks = new TaskList();
        }
        assert this.tasks != null : "Tasks must be initialized";
    }

    /**
     * Constructs an Amia instance for testing with custom file path.
     *
     * @param filePath The path to the data file.
     */
    public Amia(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        assert this.storage != null && this.ui != null : "Storage and UI must be initialized";
        try {
            this.tasks = new TaskList(storage.load());
        } catch (AmiaException e) {
            this.tasks = new TaskList();
        }
        assert this.tasks != null : "Tasks must be initialized";
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
     * Parses a command string and returns the appropriate Command object.
     *
     * @param input The user's input string.
     * @return The Command object to execute.
     * @throws AmiaException If parsing fails.
     */
    private Command parseCommand(String input) throws AmiaException {
        assert input != null : "Input cannot be null";
        CommandType cmdType = Parser.parseCommandType(input);

        switch (cmdType) {
        case TODO:
        case DEADLINE:
        case EVENT:
            return new AddCommand(input);
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
     * Generates a response for the user's input. Used by the GUI.
     *
     * @param input The user's command.
     * @return The response string to display.
     */
    public String getResponse(String input) {
        assert input != null : "Input cannot be null";
        try {
            Command command = parseCommand(input);
            assert command != null : "Command should not be null";
            String response = command.execute(tasks, ui, storage);
            isExit = command.isExit();
            return response;
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
        while (true) {
            try {
                String input = ui.readCommand();
                Command command = parseCommand(input);
                String response = command.execute(tasks, ui, storage);

                ui.showLine();
                ui.showMessage(response);
                ui.showLine();

                if (command.isExit()) {
                    ui.close();
                    return;
                }
            } catch (Exception e) {
                ui.showLine();
                ui.showMessage(e.getMessage());
                ui.showLine();
            }
        }
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
