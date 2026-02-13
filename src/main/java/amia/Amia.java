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

public class Amia {
    private static final int MAX_TASKS = 100;
    private static final String FILE_PATH = "./data/amia.txt";

    private static TaskList tasks = new TaskList();
    private static Ui ui = new Ui();
    private static Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) {
        start();
        loop();
        exit();
    }

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

    public static void markTask(String command) {
        ui.showLine();
        try {
            String args = Parser.extractIndexArg(command, "mark");
            int idx = Parser.parseIndex(args);
            tasks.markDone(idx);
            storage.save(tasks.toArrayList());
            ui.showMessage("I've marked the task as done!");
            ui.showMessage("   " + tasks.get(idx));
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

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

    public static void exit() {
        ui.showGoodbye();
    }

}
