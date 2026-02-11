import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void validateIndex(int idx) throws AmiaException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new AmiaException("... Invalid task number...");
        }
    }
    
    public void add(Task task) throws AmiaException {
        if (task == null) {
            throw new AmiaException("...Cannot add null task ...");
        }
        tasks.add(task);
    }

    public Task remove(int idx) throws AmiaException {
        validateIndex(idx);
        return tasks.remove(idx);
    }

    public Task get(int idx) throws AmiaException {
        validateIndex(idx);
        return tasks.get(idx);
    }

    public void markDone(int idx) throws AmiaException {
        validateIndex(idx);
        tasks.get(idx).markDone();
    }

    public void markUndone(int idx) throws AmiaException {
        validateIndex(idx);
        tasks.get(idx).markUndone();
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> toArrayList() {
        return tasks;
    }
}
