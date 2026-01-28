import java.util.ArrayList;
import java.util.Scanner;

public class Amia {
    private static ArrayList<String> tasks = new ArrayList<>();
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
                case "exit":
                    scanner.close();
                    return;
                case "list":
                    listTask();
                    break;
                default:
                    addTask(command);
            }
        }
    }

    public static void addTask(String command) {
        line();
        if (tasks.size() < MAX_TASKS) {
            System.out.println("\tAdded: " + command);
            tasks.add(command);
        } else {
            System.out.println("\tTask list is full!");
        }
        line();
    }

    public static void listTask() {
        line();
        if (tasks.size() == 0) {
            System.out.println("\tNo tasks to list!"); 
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    public static void start() {
        line();
        System.out.println("\tHello! I'm Amia!\n\tWhat can I do for you?");
        line();
    }

    public static void exit() {
        line();
        System.out.println("\tBye!");
        line();
    }

    public static void line() {
        System.out.println("\t-----------------------------------");
    }
}
