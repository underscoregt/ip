package amia.command;

import amia.exception.AmiaException;
import amia.exception.ErrorMessages;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Event;
import amia.task.Task;
import amia.task.TaskList;
import amia.ui.Ui;

/**
 * Represents a command to add an Event task to the task list.
 */
public class AddEventCommand extends Command {
    private String commandText;

    /**
     * Constructs an AddEventCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddEventCommand(String commandText) {
        this.commandText = commandText;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if (tasks.size() >= TaskList.MAX_TASKS) {
                throw new AmiaException(ErrorMessages.TASK_LIST_FULL);
            }

            Parser.EventInfo info = Parser.parseEvent(commandText);
            Task task = new Event(info.getDescription(), info.getFrom(), info.getTo());

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return ui.formatAddTaskMessage(task, tasks.size());
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
