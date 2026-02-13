package amia.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import amia.exception.AmiaException;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddValidTask() throws AmiaException {
        Task task = new ToDo("Read book");
        taskList.add(task);
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.get(0));
    }

    @Test
    public void testRemoveValidTask() throws AmiaException {
        Task task1 = new ToDo("Task 1");
        Task task2 = new ToDo("Task 2");
        taskList.add(task1);
        taskList.add(task2);

        Task removed = taskList.remove(0);
        assertEquals(1, taskList.size());
        assertEquals(task2, taskList.get(0));
    }

    @Test
    public void testMarkAndUnmarkTask() throws AmiaException {
        Task task = new ToDo("Task 1");
        taskList.add(task);

        assertTrue(taskList.get(0).toString().contains("[ ]"));
        taskList.markDone(0);
        assertTrue(taskList.get(0).toString().contains("[X]"));
        taskList.markUndone(0);
        assertTrue(taskList.get(0).toString().contains("[ ]"));
    }
}
