package chatterbox;
import java.util.Stack;

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

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_FIND = "find";
    private static final String CMD_UNDO = "undo";

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Stack<TaskList> history = new Stack<>();
    private CommandHandler handler;

    /**
     * Default constructor: initializes Ui, Storage and TaskList.
     */
    public Chatterbox() {
        this.ui = new Ui();
        this.storage = new Storage();
        Task[] tasks = storage.loadTasks();
        this.taskList = new TaskList(tasks, Database.getListCount());
        this.handler = new CommandHandler(taskList, history);
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
            case CMD_BYE:
                return "Bye. Hope to see you again soon!";
            case CMD_LIST:
                return taskList.printList();
            case CMD_TODO:
                return handler.handleTodo(input);
            case CMD_DEADLINE:
                return handler.handleDeadline(input);
            case CMD_EVENT:
                return handler.handleEvent(input);
            case CMD_DELETE:
                return handler.handleDelete(input);
            case CMD_MARK:
                return handler.handleMark(input);
            case CMD_UNMARK:
                return handler.handleUnmark(input);
            case CMD_FIND:
                return handler.handleFind(input);
            case CMD_UNDO:
                return handler.handleUndo();
            default:
                throw new NilException("I don't know what that means");
            }
        } catch (TodoException | DeadlineException | EventException | NilException | CommandException e) {
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
        Ui ui = new Ui();
        Chatterbox cb = new Chatterbox();
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


