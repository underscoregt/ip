package amia.command;

import amia.exception.AmiaException;
import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.size() == 0) {
                return "No tasks to list...";
            }
            StringBuilder result = new StringBuilder("Here are your tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                result.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
            }
            return result.toString().trim();
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
