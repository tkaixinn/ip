package chatterbox;

import java.util.ArrayList;
public class Chatterbox {

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage();
        Task[] tasks = storage.loadTasks();
        TaskList taskList = new TaskList(tasks, Database.getListCount());

        ui.showWelcome();

        while (true) {
            String message = ui.readCommand();
            String commandType = Parser.getCommandType(message);

            try {
                switch (commandType) {
                    case "bye":
                        ui.showMessage("Bye. Hope to see you again soon!");
                        ui.close();
                        return;
                    case "list":
                        taskList.printList(ui);
                        break;
                    case "todo":
                        if (message.equals("todo")) throw new TodoException("No empty description for a todo");
			Todo newTodo = new Todo(message.split(" ", 2)[1]);
                        taskList.addTask(newTodo);

                        ui.showMessage("Got it. I've added this task:");
			ui.showMessage(" " + newTodo);
			ui.showMessage("Now you have " + taskList.getListCount() + " tasks in the list.");
                        break;
                    case "deadline":
                        if (message.equals("deadline")) throw new DeadlineException("No empty description for a deadline");
                        Deadline newDeadline = (new Deadline(message.split(" ", 2)[1]));
			taskList.addTask(newDeadline);

                        ui.showMessage("Got it. I've added this task:");
			ui.showMessage(" " + newDeadline);
			ui.showMessage("Now you have " + taskList.getListCount() + " tasks in the list.");
                        break;
                    case "event":
                        if (message.equals("event")) throw new EventException("No empty description for an event");
                        String[] fromParts = message.split("/from", 2);
                        String[] dateParts = fromParts[1].split("/to", 2);
                        String fromTime = "From: " + dateParts[0].trim();
                        String toTime = "To: " + dateParts[1].trim();

			Event newEvent = new Event(fromParts[0].replaceFirst("event", "").trim(), fromTime, toTime);
                        taskList.addTask(newEvent);

			ui.showMessage("Got it. I've added this task:");
			ui.showMessage(" " + newEvent);
			ui.showMessage("Now you have " + taskList.getListCount() + " tasks in the list.");
                        break;
                    case "delete":
                        int index = Integer.parseInt(message.split(" ")[1]) - 1;
                        taskList.deleteTask(index);
                        ui.showMessage("Task deleted!");
                        break;
                    case "mark":
                        index = Integer.parseInt(message.split(" ")[1]) - 1;
                        taskList.markTask(index);
                        ui.showMessage("Task marked done!");
                        break;
                    case "unmark":
                        index = Integer.parseInt(message.split(" ")[1]) - 1;
                        taskList.unmarkTask(index);
                        ui.showMessage("Task unmarked!");
                        break;

                    case "find":
                        String findStr = message.split("\\s+", 2)[1];
                        int bound = taskList.getListCount();
                        ArrayList<Task> finalList = new ArrayList<>();
                        for (int i = 0; i < bound; i++) {
                            if (tasks[i].description.contains(findStr)) {
                                finalList.add(tasks[i]);
                            }
                        }
                        ui.showMessage("Here are the matching tasks in your list:");
                        for (int i = 0; i < finalList.size(); i++) {
                            ui.showMessage((i+1) + ". " + finalList.get(i).toString());
                        }
                        break;

                    default:
                        throw new NilException("I don't know what that means");
                }
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }

            storage.saveTasks(taskList.getList(), taskList.getListCount());
        }
    }
}

