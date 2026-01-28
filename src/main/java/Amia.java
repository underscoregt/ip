import java.util.Scanner;

public class Amia {
    public static void main(String[] args) {
        start();
        loop();
        exit();
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

    public static void loop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().trim();
            switch (command.toLowerCase()) {
                case "exit":
                    scanner.close();
                    return;
                default:
                    line();
                    System.out.println("\t" + command);
                    line();
            }
        }
    }

    public static void line() {
        System.out.println("\t-----------------------------------");
    }
}
