package amia.command;

import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    /**
     * Executes the command and returns the result message.
     *
     * @param tasks   The task list to operate on.
     * @param ui      The UI for displaying messages (for CLI mode).
     * @param storage The storage for saving tasks.
     * @return The result message to display.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Checks if this command causes the application to exit.
     *
     * @return True if this is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
