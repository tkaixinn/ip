package chatterbox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database handles storage of tasks for Chatterbox
 * It provides methods to load tasks from a file and save tasks
 *
 * Tasks will be stored in text file at ./data/tasks.txt
 *
 * Features:
 * - Load tasks from storage when application starts
 * - Saves tasks to storage when task list is updated
 * - Keep track of current number of tasks in storage
 */

public class Database {
    private static final String DATA_PATH = "./data/tasks.txt";
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int listCount = loadTasks(tasks);  

    public static int getListCount() {
	return listCount;
    }  

    public static void saveTasks(Task[] list, int listCount) {
        File file = new File(DATA_PATH);
        try {
             file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < listCount; i++) {
                    writer.write(list[i].toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static int loadTasks(Task[] list) {
          File file = new File(DATA_PATH);
          int listCount = 0;

          if (!file.exists()) return 0;

          try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
              String line;
              while ((line = reader.readLine()) != null && listCount < list.length) {
                  Task task = Task.fromFileString(line);
                  if (task != null) {
                     list[listCount] = task;
                     listCount++;
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }

     return listCount; // return how many tasks were loaded
     }    
}
