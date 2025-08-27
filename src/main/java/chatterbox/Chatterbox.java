package chatterbox;
import java.util.ArrayList;

/**
 * Chatterbox is the main class for Chatterbox application.
 * This handles storage operations, task management and user interactions
 *
 * Features:
 * - Add, delete and manage 3 types of tasks (Todo, Deadline, Event)
 * - Mark and unmark tasks as done
 * - Move tasks to storage
 * - Simple command-line interface for interaction
 *
 * Usage:
 * Run the application and follow the on-screen prompts to carry out tasks
 * Exceptions are handled, either for invalid input or missing task descriptions
 */



public class Chatterbox {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Default constructor: initializes Ui, Storage and TaskList.
     */
    public Chatterbox() {
        this.ui = new Ui();
        this.storage = new Storage();
        Task[] tasks = storage.loadTasks();
        this.taskList = new TaskList(tasks, Database.getListCount());
    }

    /**
     * Processes a single user input and returns the bot's response.
     * Used by both CLI and GUI.
     *
     * @param input The user command string
     * @return The response string from Chatterbox
     */
    public String getResponse(String input) {
        String commandType = Parser.getCommandType(input);
        try {
            switch (commandType) {
                case "bye":
                    return "Bye. Hope to see you again soon!";

                case "list":
                    return taskList.printList();

                case "todo":
                    if (input.equals("todo")) throw new TodoException("No empty description for a todo");
                    Todo newTodo = new Todo(input.split(" ", 2)[1]);
                    taskList.addTask(newTodo);
                    return "Got it. I've added this task:\n " + newTodo
                            + "\nNow you have " + taskList.getListCount() + " tasks in the list.";

                case "deadline":
                    if (input.equals("deadline")) throw new DeadlineException("No empty description for a deadline");
                    Deadline newDeadline = new Deadline(input.split(" ", 2)[1]);
                    taskList.addTask(newDeadline);
                    return "Got it. I've added this task:\n " + newDeadline
                            + "\nNow you have " + taskList.getListCount() + " tasks in the list.";

                case "event":
                    if (input.equals("event")) throw new EventException("No empty description for an event");
                    String[] fromParts = input.split("/from", 2);
                    String[] dateParts = fromParts[1].split("/to", 2);
                    String fromTime = "From: " + dateParts[0].trim();
                    String toTime = "To: " + dateParts[1].trim();

                    Event newEvent = new Event(fromParts[0].replaceFirst("event", "").trim(), fromTime, toTime);
                    taskList.addTask(newEvent);
                    return "Got it. I've added this task:\n " + newEvent
                            + "\nNow you have " + taskList.getListCount() + " tasks in the list.";

                case "delete":
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.deleteTask(index);
                    return "Task deleted!";

                case "mark":
                    index = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.markTask(index);
                    return "Task marked done!";

                case "unmark":
                    index = Integer.parseInt(input.split(" ")[1]) - 1;
                    taskList.unmarkTask(index);
                    return "Task unmarked!";

                case "find":
                    String findStr = input.split("\\s+", 2)[1];
                    ArrayList<Task> finalList = new ArrayList<>();
                    for (Task t : taskList.getList()) {
                        if (t.description.contains(findStr)) {
                            finalList.add(t);
                        }
                    }
                    StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                    for (int i = 0; i < finalList.size(); i++) {
                        sb.append((i + 1)).append(". ").append(finalList.get(i).toString()).append("\n");
                    }
                    return sb.toString();

                default:
                    throw new NilException("I don't know what that means");
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            storage.saveTasks(taskList.getList(), taskList.getListCount());
        }
    }

    /**
     * Console mode entry point.
     * Keeps prompting the user until "bye" is entered.
     */
    public static void main(String[] args) {
        Chatterbox cb = new Chatterbox();
        Ui ui = new Ui();

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            String response = cb.getResponse(input);
            ui.showMessage(response);

            if (input.equals("bye")) {
                ui.close();
                return;
            }
        }
    }
}


