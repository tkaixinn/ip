package chatterbox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;


/**
 * Represents an Event task in Chatterbox
 *
 * Features:
 * - Store description, start time and end time of event
 * - Prompt should include a '/from' part with data and time and a /'to' part followed by date and time
 */

public class Event extends Task implements Serializable {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Constructs an Event task with description, start time and end time
     *
     * @param description Task description
     * @param fromPart String containing start time
     * @param toPart String containing end time
     */

    public Event(String description, String fromPart, String toPart) {
        super(description);
        String fromStr = fromPart.split("\\s", 2)[1];
        String toStr = toPart.split("\\s", 2)[1];
        this.fromDateTime = LocalDateTime.parse(fromStr, inputFormatter);
        this.toDateTime = LocalDateTime.parse(toStr, inputFormatter);
    }

    @Override
    public String toString() {
        return "[" + TaskType.EVENT.getDescription() + "]" + super.toString() + " (from: " +  fromDateTime.format(outputFormatter) + " to: "
											  +  toDateTime.format(outputFormatter) + ")";
    }	

    @Override 
    public String toFileString() {
        return "EVENT | " + (isDone ? "1" : "0") + " | " + description + " | from " + fromDateTime.format(inputFormatter)
								       + " | to " + toDateTime.format(inputFormatter);
    }

}

