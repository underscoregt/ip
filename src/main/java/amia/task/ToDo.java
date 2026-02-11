package amia.task;
public class ToDo extends Task{
    public ToDo(String decsription) {
        super(decsription);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
