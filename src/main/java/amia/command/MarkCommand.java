package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private String commandText;

    /**
     * Constructs a MarkCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public MarkCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            String args = Parser.extractIndexArg(commandText, "mark");
            int idx = Parser.parseIndex(args);
            tasks.markDone(idx);
            storage.save(tasks.toArrayList());
            return "I've marked the task as done!\n   " + tasks.get(idx);
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
