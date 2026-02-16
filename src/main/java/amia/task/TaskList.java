package amia.task;

import java.util.ArrayList;

import amia.exception.AmiaException;

/**
 * Manages a list of tasks. Provides methods to add, remove, retrieve, and mark
 * tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Validates that the given index is within the bounds of the task list.
     *
     * @param index The index to validate.
     * @throws AmiaException If the index is out of bounds.
     */
    public void validateIndex(int index) throws AmiaException {
        if (index < 0 || index >= tasks.size()) {
            throw new AmiaException("... Invalid task number...");
        }
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     * @throws AmiaException If the task is null.
     */
    public void add(Task task) throws AmiaException {
        if (task == null) {
            throw new AmiaException("...Cannot add null task ...");
        }
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     * @throws AmiaException If the index is out of bounds.
     */
    public Task remove(int index) throws AmiaException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Retrieves the task at the given index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws AmiaException If the index is out of bounds.
     */
    public Task get(int index) throws AmiaException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index The index of the task to mark as done.
     * @throws AmiaException If the index is out of bounds.
     */
    public void markDone(int index) throws AmiaException {
        validateIndex(index);
        tasks.get(index).markDone();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index The index of the task to mark as not done.
     * @throws AmiaException If the index is out of bounds.
     */
    public void markUndone(int index) throws AmiaException {
        validateIndex(index);
        tasks.get(index).markUndone();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> toArrayList() {
        return tasks;
    }
}
