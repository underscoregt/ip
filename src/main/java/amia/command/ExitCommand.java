package amia.command;

import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Goodbye~";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
