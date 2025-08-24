package chatterbox;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void dummyTest() {
        try {
            Deadline task1 = new Deadline("read book/by 1/2/2020 1000");
            Todo task2 = new Todo("do chores");
            Task[] listTask = {task1, task2};
            TaskList taskList = new TaskList(listTask, 2);
            taskList.markTask(1);
            String expectedStr = "[T][X] do chores";
            assertEquals(expectedStr, taskList.getList()[1].toString());
        } catch (IndexOutOfBoundsException e) {
            //nothing
        }
    }

    @Test
    public void anotherDummyTest(){
        try {
            Deadline task1 = new Deadline("read book/by 1/2/2020 1000");
            Todo task2 = new Todo("do chores");
            Task[] listTask = {task1, task2};
            TaskList taskList = new TaskList(listTask, 2);
            taskList.deleteTask(1);
            int expectedInt = taskList.getListCount();
            assertEquals(expectedInt, 1);
        } catch (IndexOutOfBoundsException e) {
            //nothing
        }
    }
}