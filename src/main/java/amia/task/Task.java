package amia.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public boolean matches(String keyword) {
        if (keyword == null) {
            return false;
        }
        String needle = keyword.trim().toLowerCase();
        return !needle.isEmpty() && description.toLowerCase().contains(needle);
    }

    public abstract String toFileString();
}
