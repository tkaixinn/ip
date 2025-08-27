package chatterbox;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents list of tasks in Chatterbox
 * Handles adding,deleting, mark/unmarking and displaying tasks
 *
 * Features:
 * - Track current tasks
 * - Add/delete/unmark tasks
 */

public class TaskList {
    private Task[] list;
    private int listCount;

    /**
     * Constructs a TaskList with given tasks and task count
     * @param list Array of Tasks
     * @param listCount Number of valid tasks in the array
     */

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

    /**
     * Adds task to end of the list and increment the task count
     * @param task Task to add
     */

    public void addTask(Task task) {
        list[listCount] = task;
        listCount++;
    }

    /**
     * Deletes task at specified index from list and updates task count
     * @param index Task to delete
     */

    public void deleteTask(int index) {
        ArrayList<Task> newList = new ArrayList<>(Arrays.asList(list));
        newList.remove(index);
        list = newList.toArray(new Task[0]);
        listCount--;
    }

    /**
     * Marks task at specified index as done
     * @param index Index of task to mark
     */

    public void markTask(int index) {
        list[index].isDone = true;
    }

    /**
     * Marks task at specified index  (set as undone)
     * @param index Index of task to unmark
     */

    public void unmarkTask(int index) {
        list[index].isDone = false;
    }

    /**
     * Prints task in list using Ui object

     */

    public String printList() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < listCount; i++) {
            sb.append((i + 1) + ". " + list[i] + "\n");
        }
        return sb.toString().trim();
    }
}

