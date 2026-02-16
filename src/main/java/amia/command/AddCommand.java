package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Deadline;
import amia.task.Event;
import amia.task.Task;
import amia.task.TaskList;
import amia.task.ToDo;
import amia.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private static final int MAX_TASKS = 100;
    private String commandText;

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
            if (tasks.size() >= MAX_TASKS) {
                throw new AmiaException("... The task list is full...");
            }

            Task task;
            if (commandText.startsWith("todo")) {
                String description = Parser.extractDescription(commandText, "todo");
                task = new ToDo(description);
            } else if (commandText.startsWith("deadline")) {
                Parser.DeadlineInfo info = Parser.parseDeadline(commandText);
                task = new Deadline(info.getDescription(), info.getDeadline());
            } else if (commandText.startsWith("event")) {
                Parser.EventInfo info = Parser.parseEvent(commandText);
                task = new Event(info.getDescription(), info.getFrom(), info.getTo());
            } else {
                task = new ToDo(commandText);
            }

            tasks.add(task);
            storage.save(tasks.toArrayList());
            return "I've added this task!\n   " + task + "\nYou have " + tasks.size() + " task"
                    + (tasks.size() == 1 ? "" : "s") + ".";
        } catch (AmiaException e) {
            return e.getMessage();
        }
    }
}
