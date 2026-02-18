package amia.command;

import amia.exception.AmiaException;
import amia.exception.ErrorMessages;
import amia.storage.Storage;
import amia.task.Task;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Abstract base class for commands that add tasks to the task list. Implements
 * the template method pattern to eliminate code duplication.
 */
public abstract class AddCommand extends Command {
    protected String commandText;

    /**
     * Constructs an AddCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.size() >= TaskList.MAX_TASKS) {
                throw new AmiaException(ErrorMessages.TASK_LIST_FULL);
            }

            Task task = createTask();
            assert task != null : "createTask() must not return null";
            tasks.add(task);
            storage.save(tasks.toArrayList());
            return ui.formatAddTaskMessage(task, tasks.size());
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }

    /**
     * Creates the specific task type for this command. Subclasses must implement
     * this method to define how to parse the command text and create the
     * appropriate task.
     *
     * @return The created task.
     * @throws AmiaException If task creation fails.
     */
    protected abstract Task createTask() throws AmiaException;
}
