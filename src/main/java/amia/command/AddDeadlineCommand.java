package amia.command;

import amia.exception.AmiaException;
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
    private static final int MAX_TASKS = 100;
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
            if (tasks.size() >= MAX_TASKS) {
                throw new AmiaException("... The task list is full...");
            }

            Parser.DeadlineInfo info = Parser.parseDeadline(commandText);
            Task task = new Deadline(info.getDescription(), info.getDeadline());

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return "I've added this task!\n   " + task + "\nYou have " + tasks.size() + " task"
                    + (tasks.size() == 1 ? "" : "s") + ".";
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
