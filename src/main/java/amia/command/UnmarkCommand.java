package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to unmark a task (mark as not done).
 */
public class UnmarkCommand extends Command {
    private String commandText;

    /**
     * Constructs an UnmarkCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public UnmarkCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            String args = Parser.extractIndexArg(commandText, "unmark");
            int idx = Parser.parseIndex(args);
            tasks.markUndone(idx);
            storage.save(tasks.toArrayList());
            return "I've marked the task as not done yet.\n   " + tasks.get(idx);
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
