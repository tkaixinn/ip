package chatterbox;

public class Parser {

    public static String getCommandType(String message) {
        if (message.equals("list") || message.equals("bye")) {
            return message;
        } else if (message.startsWith("todo")) {
            return "todo";
        } else if (message.startsWith("deadline")) {
            return "deadline";
        } else if (message.startsWith("event")) {
            return "event";
        } else if (message.startsWith("delete")) {
            return "delete";
        } else if (message.startsWith("mark")) {
            return "mark";
        } else if (message.startsWith("unmark")) {
            return "unmark";
        } else if (message.startsWith("find")) {
            return "find";
        } else {
            return "unknown";
        }
    }
}
