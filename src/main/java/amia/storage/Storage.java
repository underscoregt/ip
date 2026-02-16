package amia.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import amia.exception.AmiaException;
import amia.task.Deadline;
import amia.task.Event;
import amia.task.Task;
import amia.task.ToDo;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final String filePath;

    /**
     * Constructs a Storage object with the given file path. Uses a default path if
     * the given path is null or empty. The default path is relative to the JAR file
     * location.
     *
     * @param filePath The path to the file where tasks will be saved.
     */
    public Storage(String filePath) {
        this.filePath = filePath == null || filePath.isEmpty() ? getDefaultFilePath() : filePath;
    }

    /**
     * Gets the default file path relative to the JAR file location.
     *
     * @return The file path for storing tasks.
     */
    private static String getDefaultFilePath() {
        try {
            // Get the location of the JAR file
            File jarFile = new File(Storage.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            // Get the directory containing the JAR
            File jarDir = jarFile.isDirectory() ? jarFile : jarFile.getParentFile();
            // Create the data file path relative to the JAR directory
            return new File(jarDir, "data/amia.txt").getPath();
        } catch (URISyntaxException e) {
            // Fall back to current directory if unable to determine JAR location
            return "./data/amia.txt";
        }
    }

    /**
     * Loads tasks from the storage file. Returns an empty list if the file does not
     * exist.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws AmiaException If an error occurs while reading the file.
     */
    public ArrayList<Task> load() throws AmiaException {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" \\| ");

                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                Task task;
                switch (type) {
                case "T":
                    task = new ToDo(parts[2]);
                    break;
                case "D":
                    task = new Deadline(parts[2], LocalDateTime.parse(parts[3], FILE_FORMAT).format(INPUT_FORMAT));
                    break;
                case "E":
                    task = new Event(parts[2], LocalDateTime.parse(parts[3], FILE_FORMAT).format(INPUT_FORMAT),
                            LocalDateTime.parse(parts[4], FILE_FORMAT).format(INPUT_FORMAT));
                    break;
                default:
                    continue;
                }

                if (isDone) {
                    task.markDone();
                }

                tasks.add(task);
            }
        } catch (IOException e) {
            throw new AmiaException("... I can't load the saved tasks...");
        }

        return tasks;
    }

    /**
     * Saves tasks to the storage file. Creates the directory if it does not exist.
     *
     * @param tasks The ArrayList of tasks to save.
     * @throws AmiaException If an error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws AmiaException {
        try {
            File dir = new File(new File(filePath).getParent());
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (Task task : tasks) {
                bw.write(task.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new AmiaException("... I can't save the tasks...");
        }
    }
}
