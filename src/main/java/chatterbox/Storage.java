package chatterbox;

/**
 * Storage handles loading and saving tasks for Chatterbox
 * Features:
 * - Load tasks from storage into memory
 * - Save tasks from memory to storage
 */

public class Storage {

    public Task[] loadTasks() {
        Task[] list = new Task[100];
        int listCount = Database.loadTasks(list);
        return list;
    }

    public void saveTasks(Task[] list, int listCount) {
        Database.saveTasks(list, listCount);
    }
}
