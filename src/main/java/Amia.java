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
            String command = scanner.nextLine().trim();
            switch (command.toLowerCase()) {
                case "bye":
                    scanner.close();
                    return;
                case "list":
                    listTask();
                    break;
                default:
                    if (command.startsWith("mark ")) {
                        markTask(command);
                    } else if (command.startsWith("unmark ")) {
                        unmarkTask(command);
                    } else {
                        addTask(command);
                    }
            }
        }
    }

    public static void addTask(String command) {
        say(line());
        if (tasks.size() < MAX_TASKS) {
            say("Added: " + command);
            tasks.add(new Task(command));
        } else {
            say("Task list is full!");
        }
        say(line());
    }

    public static void listTask() {
        say(line());
        if (tasks.size() == 0) {
            say("No tasks to list!"); 
            say(line());
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            say((i + 1) + ". " + tasks.get(i));
        }
        say(line());
    }

    public static void markTask(String command) {
        say(line());
        try { 
            int idx = Integer.parseInt(command.substring(5)) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markDone();
                say("I've marked the task as done!");
                say("  " + tasks.get(idx));
            } else {
                say("Invalid task!");
            }
        } catch (NumberFormatException e) {
            say(e.toString());
        }
        say(line());
    }

    public static void unmarkTask(String command) {
        say(line());
        try { 
            int idx = Integer.parseInt(command.substring(7)) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.get(idx).markUndone();
                say("I've marked the task as not done yet.");
                say("   " + tasks.get(idx));
            } else {
                say("Invalid task!");
            }
        } catch (NumberFormatException e) {
            say(e.toString());
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
