import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks == null ? new ArrayList<>() : tasks;
    }

    public int size() {
        return tasks.size();
    }

    public void add(Task task) throws AmiaException {
        if (task == null) {
            throw new AmiaException("...Cannot add null task ...");
        }
        tasks.add(task);
    }

    public Task remove(int index) throws AmiaException {
        if (index < 0 || index >= tasks.size()) {
            throw new AmiaException("...Invalid task number...");
        }
        return tasks.remove(index);
    }

    public Task get(int index) throws AmiaException {
        if (index < 0 || index >= tasks.size()) {
            throw new AmiaException("...Invalid task number...");
        }
        return tasks.get(index);
    }

    public void markDone(int index) throws AmiaException {
        get(index).markDone();
    }

    public void markUndone(int index) throws AmiaException {
        get(index).markUndone();
    }

    public ArrayList<Task> toArrayList() {
        return tasks;
    }
}
