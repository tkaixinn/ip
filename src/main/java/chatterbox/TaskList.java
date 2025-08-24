package chatterbox;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskList {
    private Task[] list;
    private int listCount;

    public TaskList(Task[] list, int listCount) {
        this.list = list;
        this.listCount = listCount;
    }

    public Task[] getList() {
        return list;
    }

    public int getListCount() {
        return listCount;
    }

    public void addTask(Task task) {
        list[listCount] = task;
        listCount++;
    }

    public void deleteTask(int index) {
        ArrayList<Task> newList = new ArrayList<>(Arrays.asList(list));
        newList.remove(index);
        list = newList.toArray(new Task[0]);
        listCount--;
    }

    public void markTask(int index) {
        list[index].isDone = true;
    }

    public void unmarkTask(int index) {
        list[index].isDone = false;
    }

    public void printList(Ui ui) {
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < listCount; i++) {
            ui.showMessage((i + 1) + ". " + list[i]);
        }
    }
}

