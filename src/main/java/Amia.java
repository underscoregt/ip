import java.util.ArrayList;
import java.util.Scanner;

public class Amia {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int MAX_TASKS = 100;

    public static void main(String[] args) {
        start();
        loop();
        exit();
    }

    public static void loop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String command = scanner.nextLine().trim();
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
                        scanner.close();
                        return;
                    case UNKNOWN:
                        throw new AmiaException("...?");
                }
            } catch (AmiaException e) {
                say(line());
                say(e.getMessage());
                say(line());
            }
        }
    }

    public static void addTask(String command) {
        say(line());
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
                say("I've added this task!");
                say("   " + task);
                say("You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + ".");
            } else {
                throw new AmiaException("... The task list is full...");
            }
        } catch (AmiaException e) {
            say(e.getMessage());
        }
        say(line());
    }

    public static void deleteTask(String command) {
        say(line());
        try { 
            String args = command.substring(6).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: delete <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                Task removedTask = tasks.remove(idx);
                say("I've removed this task:");
                say("   " + removedTask);
                say("You have " + tasks.size() + " task" + (tasks.size() == 1 ? "" : "s") + ".");
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            say(e.getMessage());
        }
        say(line());
    }

    public static void listTask() {
        say(line());
        if (tasks.size() == 0) {
            say("No tasks to list..."); 
            say(line());
            return;
        }
        say("Here are your tasks: ");
        for (int i = 0; i < tasks.size(); i++) {
            say((i + 1) + ". " + tasks.get(i));
        }
        say(line());
    }

    public static void markTask(String command) {
        say(line());
        try { 
            String args = command.substring(4).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: mark <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markDone();
                say("I've marked the task as done!");
                say("   " + tasks.get(idx));
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            say(e.getMessage());
        }
        say(line());
    }

    public static void unmarkTask(String command) {
        say(line());
        try { 
            String args = command.substring(6).trim();
            if (args.isEmpty()) {
                throw new AmiaException("... Invalid format... Use: unmark <index>");
            }
            int idx = Integer.parseInt(args) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markUndone();
                say("I've marked the task as not done yet.");
                say("   " + tasks.get(idx));
            } else {
                throw new AmiaException("... Invalid task number...");
            }
        } catch (AmiaException e) {
            say(e.getMessage());
        }
        say(line());
    }

    public static void start() {
        say(line());
        say("Hello! I'm Amia!");
        say("What can I do for you?");
        say(line());
    }

    public static void exit() {
        say(line());
        say("Bye!");
        say(line());
    }

    public static void say(String s) {
        System.out.println("\t" + s);
    }

    public static String line() {
        return "-----------------------------------";
    }
}
