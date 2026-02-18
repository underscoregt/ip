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
            String arguments = Parser.extractIndexArgument(commandText, "unmark");
            int[] indices = Parser.parseIndices(arguments);
            
            if (indices.length == 1) {
                tasks.markUndone(indices[0]);
                storage.save(tasks.toArrayList());
                return "I've marked the task as not done yet.\n   " + tasks.get(indices[0]);
            } else {
                StringBuilder response = new StringBuilder("I've marked these tasks as not done yet:\n");
                for (int index : indices) {
                    tasks.markUndone(index);
                    response.append("   ").append(tasks.get(index)).append("\n");
                }
                storage.save(tasks.toArrayList());
                return response.toString().trim();
            }
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
