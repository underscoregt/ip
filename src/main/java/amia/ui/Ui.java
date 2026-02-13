package amia.ui;

import java.util.Scanner;

/**
 * Handles the user interface and user input/output.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command line from the user.
     *
     * @return The user's command with leading and trailing whitespace removed.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Displays a message to the user with indentation.
     *
     * @param s The message to display.
     */
    public void showMessage(String s) {
        System.out.println("\t" + s);
    }

    /**
     * Displays a line separator.
     */
    public void showLine() {
        System.out.println("-----------------------------------");
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        showMessage("Hello! I'm Amia!");
        showMessage("What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showLine();
        showMessage("Bye!");
        showLine();
    }
}
