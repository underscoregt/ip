import java.util.ArrayList;

public class Amia {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int MAX_TASKS = 100;
    private static Ui ui = new Ui();

    public static void main(String[] args) {
        start();
        loop();
        exit();
    }

    public static void loop() {
        while (true) {
            try {
                String command = ui.readCommand();
                CommandType cmdType = CommandType.fromString(command.toLowerCase());
                
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
                    String desc = command.substring(4).trim();
                    if (desc.isEmpty()) {
                        throw new AmiaException("... The description of a task cannot be empty...");
                    }
                    task = new ToDo(desc);
                } else if (command.startsWith("deadline")) {
                    String args = command.substring(8).trim();
                    int byIdx = args.lastIndexOf("/by");
                    if (byIdx == -1 || args.isEmpty()) {
                        throw new AmiaException("Invalid format... Use: deadline <desc> /by <date>");
                    }
                    String desc = args.substring(0, byIdx).trim();
                    String by = args.substring(byIdx + 3).trim();
                    if (desc.isEmpty()) {
                        throw new AmiaException("... The description of a task can't be empty...");
                    }
                    if (by.isEmpty()) {
                        throw new AmiaException("... The deadline can't be empty...");
                    }
                    task = new Deadline(desc, by);
                } else if (command.startsWith("event")) {
                    String args = command.substring(5).trim();
                    int fromIdx = args.lastIndexOf("/from");
                    int toIdx = args.lastIndexOf("/to");
                    if (fromIdx == -1 || toIdx == -1 || args.isEmpty()) {
                        throw new AmiaException("Invalid format... Use: event <desc> /from <start> /to <end>");
                    }
                    String desc = args.substring(0, fromIdx).trim();
                    String from = args.substring(fromIdx + 5, toIdx).trim();
                    String to = args.substring(toIdx + 3).trim();
                    if (desc.isEmpty()) {
                        throw new AmiaException("...The description of a task can't be empty...");
                    }
                    if (from.isEmpty()) {
                        throw new AmiaException("... The start time can't be empty...");
                    }
                    if (to.isEmpty()) {
                        throw new AmiaException("... The end time can't be empty...");
                    }
                    task = new Event(desc, from, to);
                } else {
                    // should never reach here
                    task = new ToDo(command);
                }

                tasks.add(task);
                Storage.save(tasks);
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
            String args = command.substring(6).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: delete <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                Task removedTask = tasks.remove(idx);
                Storage.save(tasks);
                ui.showMessage("I've removed this task:");
                ui.showMessage("   " + removedTask);
                ui.showMessage("You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + ".");
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public static void listTask() {
        ui.showLine();
        if (tasks.size() == 0) {
            ui.showMessage("No tasks to list..."); 
            ui.showLine();
            return;
        }
        ui.showMessage("Here are your tasks: ");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + ". " + tasks.get(i));
        }
        ui.showLine();
    }

    public static void markTask(String command) {
        ui.showLine();
        try { 
            String args = command.substring(4).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: mark <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markDone();
                Storage.save(tasks);
                ui.showMessage("I've marked the task as done!");
                ui.showMessage("   " + tasks.get(idx));
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public static void unmarkTask(String command) {
        ui.showLine();
        try { 
            String args = command.substring(6).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: unmark <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markUndone();
                Storage.save(tasks);
                ui.showMessage("I've marked the task as not done yet.");
                ui.showMessage("   " + tasks.get(idx));
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }

    public static void start() {
        ui.showLine();
        try {
            tasks = Storage.load();
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
