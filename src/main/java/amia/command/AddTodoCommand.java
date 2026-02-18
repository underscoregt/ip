package amia.command;

import amia.exception.AmiaException;
import amia.parser.Parser;
import amia.task.Task;
import amia.task.ToDo;

/**
 * Represents a command to add a ToDo task to the task list.
 */
public class AddTodoCommand extends AddCommand {

    /**
     * Constructs an AddTodoCommand with the given command text.
     *
     * @param commandText The full command string.
     */
    public AddTodoCommand(String commandText) {
        super(commandText);
    }

    @Override
    protected Task createTask() throws AmiaException {
        String description = Parser.extractDescription(commandText, "todo");
        return new ToDo(description);
    }
}
