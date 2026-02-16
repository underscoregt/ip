package amia.command;

import amia.exception.AmiaException;
import amia.exception.ErrorMessages;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Deadline;
import amia.task.Task;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to add a Deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private String commandText;

    /**
     * Constructs an AddDeadlineCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddDeadlineCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.size() >= TaskList.MAX_TASKS) {
                throw new AmiaException(ErrorMessages.TASK_LIST_FULL);
            }

            Parser.DeadlineInfo info = Parser.parseDeadline(commandText);
            Task task = new Deadline(info.getDescription(), info.getDeadline());

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return ui.formatAddTaskMessage(task, tasks.size());
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
