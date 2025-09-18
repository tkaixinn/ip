package chatterbox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import chatterbox.DeadlineException;

/**
 * Parser determines the type of command a user entered for Chatterbox
 *
 * Features:
 * - Analyse user input string
 * - Identify which command it corresponds to
 */

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
        } else if (message.startsWith("undo")) {
            return "undo";
        } else {
            return "unknown";
        }
    }

    private static final DateTimeFormatter DEADLINE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Parses a Todo description from the user input.
     *
     * @param input The full command string (e.g., "todo read book").
     * @return The description of the Todo task.
     * @throws TodoException If no description is provided after "todo".
     */
    public static String parseTodoDescription(String input) throws TodoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TodoException("Todo requires a description.");
        }
        return parts[1].trim();
    }

    /**
     * Parses a Deadline description from the user input and validates its date/time format.
     *
     * @param input The full command string (e.g., "deadline return book /by 12/12/25 1800").
     * @return The description string containing both the task and the "/by" clause.
     * @throws DeadlineException If the description is missing, "/by" is not present,
     *                           or the date/time format is invalid.
     */
    public static String parseDeadlineDescription(String input) throws DeadlineException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DeadlineException("Deadline requires a description with /by.");
        }
        String description = parts[1].trim();
        if (!description.contains("/by")) {
            throw new DeadlineException("Deadline must include '/by DD/MM/YYYY HhMm'.");
        }
        String[] descParts = description.split("/by", 2);
        String taskDesc = descParts[0].trim();
        String byPart = descParts[1].trim();
        if (byPart.isEmpty()) {
            throw new DeadlineException("Deadline requires a date/time after '/by'. Format: DD/MM/YYYY HhMm");
        }
        try {
            LocalDateTime.parse(byPart, DEADLINE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DeadlineException("Invalid date/time format. Use 'DD/MM/YYYY HhMm'.");
        }
        return description;
    }

    /**
     * Parses an Event description from the user input and validates that it has
     * a description, a "/from" datetime, and a "/to" datetime.
     *
     * @param input The full command string
     * @return A String array containing:
     *         [0] the event description,
     *         [1] the start datetime string,
     *         [2] the end datetime string.
     * @throws EventException If description, /from, or /to parts are missing, or
     *                        if date/time format is invalid.
     */
    public static String[] parseEventParts(String input) throws EventException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EventException("Event requires a description with /from and /to.");
        }
        if (!parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new EventException("Event must include '/from DD/MM/YYYY HhMm /to DD/MM/YYYY HhMm'.");
        }
        String[] fromParts = parts[1].split("/from", 2);
        String[] dateParts = fromParts[1].split("/to", 2);
        String description = fromParts[0].trim();
        String fromPart = dateParts[0].trim();
        String toPart = dateParts[1].trim();
        if (fromPart.isEmpty() || toPart.isEmpty()) {
            throw new EventException("Event requires both start and end times." +
                                     "Format: '/from DD/MM/YYYY HhMm /to DD/MM/YYYY HhMm'");
        }
        try {
            LocalDateTime.parse(fromPart, DEADLINE_FORMATTER);
            LocalDateTime.parse(toPart, DEADLINE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new EventException("Invalid date/time format. Use 'DD/MM/YYYY HhMm'.");
        }
        return new String[]{description, fromPart, toPart};
    }

    public static int parseIndex(String input, String command, int listSize) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 2) {
            throw new CommandException("The " + command + " command requires a task number.");
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0) {
                throw new CommandException("Task number must be positive.");
            }
            if (index >= listSize) {
                throw new CommandException(
                        "Task number is out of range. You have only " + listSize + " tasks."
                );
            }
            return index;
        } catch (NumberFormatException e) {
            throw new CommandException("Task number must be a valid integer.");
        }
    }

    public static String parseFind(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length < 2 || parts[1].isEmpty()) {
            throw new CommandException("The find command requires a keyword. Example: find book");
        }
        return parts[1].trim();
    }
}

