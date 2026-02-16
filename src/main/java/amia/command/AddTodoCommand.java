package amia.command;

import amia.exception.AmiaException;
import amia.exception.ErrorMessages;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Task;
import amia.task.TaskList;
import amia.task.ToDo;
import amia.ui.Ui;

/**
 * Represents a command to add a ToDo task to the task list.
 */
public class AddTodoCommand extends Command {
    private static final int MAX_TASKS = 100;
    private String commandText;

    /**
     * Constructs an AddTodoCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddTodoCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.size() >= MAX_TASKS) {
                throw new AmiaException(ErrorMessages.TASK_LIST_FULL);
            }

            String description = Parser.extractDescription(commandText, "todo");
            Task task = new ToDo(description);

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return "I've added this task!\n   " + task + "\nYou have " + tasks.size() + " task"
                    + (tasks.size() == 1 ? "" : "s") + ".";
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
