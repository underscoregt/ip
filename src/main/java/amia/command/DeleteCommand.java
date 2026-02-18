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
        try {
            String arguments = Parser.extractIndexArgument(commandText, "delete");
            int[] indices = Parser.parseIndices(arguments);
            
            if (indices.length == 1) {
                Task removedTask = tasks.remove(indices[0]);
                storage.save(tasks.toArrayList());
                return "I've removed this task:\n   " + removedTask + "\n" + ui.formatTaskCountMessage(tasks.size());
            } else {
                StringBuilder response = new StringBuilder("I've removed these tasks:\n");
                for (int index : indices) {
                    Task removedTask = tasks.remove(index);
                    response.append("   ").append(removedTask).append("\n");
                }
                storage.save(tasks.toArrayList());
                response.append(ui.formatTaskCountMessage(tasks.size()));
                return response.toString();
            }
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
