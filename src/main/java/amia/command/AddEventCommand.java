package amia.command;

import amia.exception.AmiaException;
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
    private static final int MAX_TASKS = 100;
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
            if (tasks.size() >= MAX_TASKS) {
                throw new AmiaException("... The task list is full...");
            }

            Parser.EventInfo info = Parser.parseEvent(commandText);
            Task task = new Event(info.getDescription(), info.getFrom(), info.getTo());

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return "I've added this task!\n   " + task + "\nYou have " + tasks.size() + " task"
                    + (tasks.size() == 1 ? "" : "s") + ".";
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
