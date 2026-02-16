package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Task;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to find tasks by keyword.
 */
public class FindCommand extends Command {
    private String commandText;

    /**
     * Constructs a FindCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public FindCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            String keyword = Parser.extractDescription(commandText, "find");
            StringBuilder result = new StringBuilder("Here are the matching tasks:\n");
            boolean foundAny = false;
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.matches(keyword)) {
                    result.append((i + 1)).append(". ").append(task).append("\n");
                    foundAny = true;
                }
            }
            if (!foundAny) {
                result.append("No matching tasks found.");
            }
            return result.toString().trim();
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
