package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.task.Event;
import amia.task.Task;

/**
 * Represents a command to add an Event task to the task list.
 */
public class AddEventCommand extends AddCommand {

    /**
     * Constructs an AddEventCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddEventCommand(String commandText) {
        super(commandText);
    }

    @Override
    protected Task createTask() throws AmiaException {
        Parser.EventInfo info = Parser.parseEvent(commandText);
        return new Event(info.getDescription(), info.getFrom(), info.getTo());
    }
}
