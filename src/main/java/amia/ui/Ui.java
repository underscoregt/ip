package amia.ui;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public void showMessage(String s) {
        System.out.println("\t" + s);
    }

    public void showLine() {
        System.out.println("-----------------------------------");
    }

    public void showWelcome() {
        showLine();
        showMessage("Hello! I'm Amia!");
        showMessage("What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        showMessage("Bye!");
        showLine();
    }
}
