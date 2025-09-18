package chatterbox;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Database handles storage of tasks for Chatterbox
 * It provides methods to load tasks from a file and save tasks
 *
 * Tasks will be stored in text file at ./data/tasks.txt
 */
public class Database {
    private static final String DATA_PATH = "./data/tasks.txt";
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int listCount = loadTasks(tasks);

    public static int getListCount() {
        return listCount;
    }

    /**
     * Saves the given list of tasks to the data file
     * Each task is written in a line using its file string representation.
     *
     * @param list      The array of tasks to save
     * @param listCount The number of tasks currently in the list
     */
    public static void saveTasks(Task[] list, int listCount) {
        File file = new File(DATA_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace(); return;
        }

        for (int i = 0; i < listCount; i++) {
            try { writer.write(list[i].toFileString()); writer.newLine(); }
            catch (IOException e) { e.printStackTrace(); }
        }

        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads tasks from the data file into the given list.
     * Tasks are reconstructed from their file string representation.
     *
     * @param list The array to populate with loaded tasks
     * @return The number of tasks successfully loaded into the list
     */
    public static int loadTasks(Task[] list) {
        File file = new File(DATA_PATH);
        if (!file.exists()) return 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace(); return 0;
        }
        int count = 0;
        while (true) {
            String line = null;
            try { line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (line == null || count >= list.length) {
                break;
            }
            Task task = Task.fromFileString(line);
            if (task != null) list[count++] = task;
        }
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
