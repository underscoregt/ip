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
            String arguments = Parser.extractIndexArgument(commandText, "mark");
            int[] indices = Parser.parseIndices(arguments);
            
            if (indices.length == 1) {
                tasks.markDone(indices[0]);
                storage.save(tasks.toArrayList());
                return "I've marked the task as done!\n   " + tasks.get(indices[0]);
            } else {
                StringBuilder response = new StringBuilder("I've marked these tasks as done:\n");
                for (int index : indices) {
                    tasks.markDone(index);
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
