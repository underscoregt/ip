package amia.command;

import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents an unknown or invalid command.
 */
public class UnknownCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "...?";
    }
}
