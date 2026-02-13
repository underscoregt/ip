package amia;

import amia.exception.AmiaException;
import amia.parser.CommandType;
import amia.parser.Parser;
import amia.storage.Storage;
import amia.task.Deadline;
import amia.task.Event;
import amia.task.Task;
import amia.task.TaskList;
import amia.task.ToDo;
import amia.ui.Ui;

/**
 * Main application class for the Amia task manager. Handles the main
 * application loop and task management operations.
 */
public class Amia {
    private static final int MAX_TASKS = 100;
    private static final String FILE_PATH = "./data/amia.txt";

    private static TaskList tasks = new TaskList();
    private static Ui ui = new Ui();
    private static Storage storage = new Storage(FILE_PATH);

    /**
     * Starts the application and runs the main loop.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        start();
        loop();
        exit();
    }

    /**
     * Continuously reads user commands and processes them until the user exits.
     */
    public static void loop() {
        while (true) {
            try {
                String command = ui.readCommand();
                CommandType cmdType = Parser.parseCommandType(command);

                switch (cmdType) {
                case TODO:
                case DEADLINE:
                case EVENT:
                    addTask(command);
                    break;
                case MARK:
                    markTask(command);
                    break;
                case UNMARK:
                    unmarkTask(command);
                    break;
                case DELETE:
                    deleteTask(command);
                    break;
                case LIST:
                    listTask();
                    break;
                case FIND:
                    findTask(command);
                    break;
                case BYE:
                    ui.close();
                    return;
                case UNKNOWN:
                    throw new AmiaException("...?");
                }
            } catch (AmiaException e) {
                ui.showLine();
                ui.showMessage(e.getMessage());
                ui.showLine();
            }
        }
    }

    /**
     * Adds a new task to the task list based on the command provided. Supports
     * todo, deadline, and event task types.
     *
     * @param command The command string containing task type and details.
     */
    public static void addTask(String command) {
        ui.showLine();
        try {
            if (tasks.size() < MAX_TASKS) {
                Task task;
                if (command.startsWith("todo")) {
                    String desc = Parser.extractDescription(command, "todo");
                    task = new ToDo(desc);
                } else if (command.startsWith("deadline")) {
                    Parser.DeadlineInfo info = Parser.parseDeadline(command);
                    task = new Deadline(info.getDescription(), info.getDeadline());
                } else if (command.startsWith("event")) {
                    Parser.EventInfo info = Parser.parseEvent(command);
                    task = new Event(info.getDescription(), info.getFrom(), info.getTo());
                } else {
                    task = new ToDo(command);
                }

                tasks.add(task);
                storage.save(tasks.toArrayList());
                ui.showMessage("I've added this task!");
                ui.showMessage("   " + task);
                ui.showMessage("You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + ".");
            } else {
                throw new AmiaException("... The task list is full...");
            }
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    /**
     * Deletes a task from the task list based on the index provided in the command.
     *
     * @param command The delete command containing the task index.
     */
    public static void deleteTask(String command) {
        ui.showLine();
        try {
            String args = Parser.extractIndexArg(command, "delete");
            int idx = Parser.parseIndex(args);
            Task removedTask = tasks.remove(idx);
            storage.save(tasks.toArrayList());
            ui.showMessage("I've removed this task:");
            ui.showMessage("   " + removedTask);
            ui.showMessage("You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + ".");
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    /**
     * Displays all tasks in the task list.
     */
    public static void listTask() {
        ui.showLine();
        try {
            if (tasks.size() == 0) {
                ui.showMessage("No tasks to list...");
                ui.showLine();
                return;
            }
            ui.showMessage("Here are your tasks: ");
            for (int i = 0; i < tasks.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasks.get(i));
            }
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    /**
     * Marks a task as not done based on the index provided in the command.
     *
     * @param command The unmark command containing the task index.
     */
    public static void unmarkTask(String command) {
        ui.showLine();
        try {
            String args = Parser.extractIndexArg(command, "unmark");
            int idx = Parser.parseIndex(args);
            tasks.markUndone(idx);
            storage.save(tasks.toArrayList());
            ui.showMessage("I've marked the task as not done yet.");
            ui.showMessage("   " + tasks.get(idx));
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    /**
     * Initializes the application by loading previously saved tasks and displaying
     * a welcome message.
     */
    public static void start() {
        ui.showLine();
        try {
            tasks = new TaskList(storage.load());
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showMessage("Hello! I'm Amia!");
        ui.showMessage("What can I do for you?");
        ui.showLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public static void exit() {
        ui.showGoodbye();
    }

}
