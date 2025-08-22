import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Event extends Task implements Serializable { 
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, String fromPart, String toPart) { 
	super(description);
	
	String fromStr = fromPart.split("\\s", 2)[1];
	String toStr = toPart.split("\\s", 2)[1];

	this.fromDateTime = LocalDateTime.parse(fromStr, inputFormatter);
	this.toDateTime = LocalDateTime.parse(toStr, inputFormatter);
    }

    @Override
    public String toString() {
	return "[" + TaskType.EVENT.getDescription() + "]" + super.toString() + "(from: " +  fromDateTime.format(outputFormatter) + " to: "  
											  +  toDateTime.format(outputFormatter) + ")";
    }	

    @Override 
    public String toFileString() {
	return "EVENT | " + (isDone ? "1" : "0") + " | " + description + " | from " + fromDateTime.format(inputFormatter) 
								       + " | to " + toDateTime.format(inputFormatter);
    }

}

