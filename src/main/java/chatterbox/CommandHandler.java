package chatterbox;

import java.util.ArrayList;
import java.util.Stack;

public class CommandHandler {
    private TaskList taskList;
    private Stack<TaskList> history;

    public CommandHandler(TaskList taskList, Stack<TaskList> history) {
        this.taskList = taskList;
        this.history = history;
    }

    /**
     * Save current state before making a change.
     */
    private void saveState() {
        Task[] snapshotArray = java.util.Arrays.stream(taskList.getList())
                .limit(taskList.getListCount())
                .map(Task::clone)
                .toArray(Task[]::new);
        TaskList snapshot = new TaskList(snapshotArray, taskList.getListCount());
        history.push(snapshot);
    }
    /**
     * Formats a list of tasks into a numbered string output with a header.
     * This method uses Java varargs to accept a variable number of Task objects.
     * This is a helper function to allow case CMD_FIND to work in getResponse()
     *
     * @param header A header line to appear before the list of tasks.
     * @param tasks  Variable number of Task objects to format and include in the output.
     * @return A single string containing the header and numbered list of tasks, each on a new line.
     */
    private String formatTasks(String header, Task... tasks) {
        StringBuilder sb = new StringBuilder(header + "\n");
        for (int i = 0; i < tasks.length; i++) {
            sb.append(i + 1).append(". ").append(tasks[i]).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Formats the response message after adding a task.
     *
     * @param task The Task object that was added.
     * @return A message confirming that the task was added and showing the current task count.
     */
    private String formatAddResponse(Task task) {
        return "Got it. I've added this task:\n " + task
                + "\nNow you have " + taskList.getListCount() + " tasks in the list.";
    }

    /**
     * Handles adding a new Todo task.
     *
     * @param input The raw input string from the user.
     * @return A formatted message confirming the addition of the Todo task.
     * @throws TodoException If the Todo description is missing or invalid.
     */
    public String handleTodo(String input) throws TodoException {
        saveState();
        String description = Parser.parseTodoDescription(input);
        Todo newTodo = new Todo(description);
        taskList.addTask(newTodo);
        return formatAddResponse(newTodo);
    }

    /**
     * Handles adding a new Deadline task.
     *
     * @param input The raw input string from the user.
     * @return A formatted message confirming the addition of the Deadline task.
     * @throws DeadlineException If the Deadline description is missing, invalid, or has an invalid date/time.
     */
    public String handleDeadline(String input) throws DeadlineException {
        saveState();
        String description = Parser.parseDeadlineDescription(input);
        Deadline newDeadline = new Deadline(description);
        taskList.addTask(newDeadline);
        return formatAddResponse(newDeadline);
    }

    /**
     * Handles adding a new Event task.
     *
     * @param input The raw input string from the user.
     * @return A formatted message confirming the addition of the Event task.
     * @throws EventException If the Event description is missing, or if the /from or /to fields are invalid.
     */
    public String handleEvent(String input) throws EventException {
        saveState();
        String[] parts = Parser.parseEventParts(input);
        Event newEvent = new Event(parts[0], "From: " + parts[1], "To: " + parts[2]);
        taskList.addTask(newEvent);
        return formatAddResponse(newEvent);
    }
    /**
     *
     * Handles marking a task as done.
     *
     * @param input The raw input string from the user.
     * @return A message confirming the task was marked as done.
     * @throws CommandException If the index is missing, invalid, or out of bounds.
     */
    public String handleMark(String input) throws CommandException {
        saveState();
        int index = Parser.parseIndex(input, "mark", taskList.getListCount());
        taskList.markTask(index);
        return "Task marked done!";
    }

    /**
     * Handles deleting a task.
     *
     * @param input The raw input string from the user.
     * @return A message confirming the task was deleted.
     * @throws CommandException If the index is missing, invalid, or out of bounds.
     */
    public String handleDelete(String input) throws CommandException {
        saveState();
        int index = Parser.parseIndex(input, "delete", taskList.getListCount());
        taskList.deleteTask(index);
        return "Task deleted!";
    }


    /**
     * Handles unmarking a task (setting it as not done).
     *
     * @param input The raw input string from the user.
     * @return A message confirming the task was unmarked.
     * @throws CommandException If the index is missing, invalid, or out of bounds.
     */
    public String handleUnmark(String input) throws CommandException {
        saveState();
        int index = Parser.parseIndex(input, "unmark", taskList.getListCount());
        taskList.unmarkTask(index);
        return "Task unmarked!";
    }

    /**
     * Handles finding tasks that contain a search string in their description.
     *
     * @param input The raw input string containing the search query.
     * @return A formatted list of matching tasks, or a message if none are found.
     * @throws CommandException If the search string is missing or invalid.
     */
    public String handleFind(String input) throws CommandException {
        String findStr = Parser.parseFind(input);
        ArrayList<Task> finalList = new ArrayList<>();
        for (Task t : taskList.getList()) {
            if (t != null && t.getDescription().contains(findStr)) {
                finalList.add(t);
            }
        }
        if (finalList.isEmpty()) {
            return "No matching tasks found for: " + findStr;
        }
        return formatTasks("Here are the matching tasks in your list:", finalList.toArray(new Task[0]));
    }

    /**
     * Handles undoing the last action on the task list.
     *
     * @return A message confirming the last action was undone, or a message indicating nothing to undo.
     */
    public String handleUndo() {
        if (history.isEmpty()) return "Nothing to undo!";
        taskList = history.pop();
        return "Last action undone!";
    }
}

