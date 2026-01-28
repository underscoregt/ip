public class ToDo extends Task{
    public ToDo(String decsription) {
        super(decsription);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
