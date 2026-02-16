package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Task;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private String commandText;

    /**
     * Constructs a DeleteCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public DeleteCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null && storage != null : "Tasks and storage cannot be null";
        try {
            String args = Parser.extractIndexArg(commandText, "delete");
            int idx = Parser.parseIndex(args);
            Task removedTask = tasks.remove(idx);
            storage.save(tasks.toArrayList());
            return "I've removed this task:\n   " + removedTask + "\nYou have " + tasks.size() + " task"
                    + (tasks.size() == 1 ? "" : "s") + ".";
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
