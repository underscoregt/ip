package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.task.Deadline;
import amia.task.Task;

/**
 * Represents a command to add a Deadline task to the task list.
 */
public class AddDeadlineCommand extends AddCommand {

    /**
     * Constructs an AddDeadlineCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddDeadlineCommand(String commandText) {
        super(commandText);
    }

    @Override
    protected Task createTask() throws AmiaException {
        Parser.DeadlineInfo info = Parser.parseDeadline(commandText);
        return new Deadline(info.getDescription(), info.getDeadline());
    }
}
