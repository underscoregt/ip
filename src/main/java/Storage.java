import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath == null || filePath.isEmpty() ? "./data/amia.txt" : filePath;
    }

    public ArrayList<Task> load() throws AmiaException {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            return tasks; // no file yet = no tasks
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
                        task = new Deadline(parts[2], parts[3]);
                        break;
                    case "E":
                        task = new Event(parts[2], parts[3], parts[4]);
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
