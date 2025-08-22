import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task implements Serializable { 
    public LocalDateTime byDateTime;

    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String description) {
	super(description.split("/", 2)[0]);
	String datePart = description.split("/", 2)[1].replaceFirst("by\\s*", "");
        this.byDateTime = LocalDateTime.parse(datePart, inputFormatter);
    } 

    @Override 
    public String toString() { 
	return "[" + TaskType.DEADLINE.getDescription() + "]" + super.toString() + "(by: " + byDateTime.format(outputFormatter) + ")";

    }

    @Override
    public String toFileString() {
	return "DEADLINE | " + (isDone ? "1" : "0") + " | " + description + " / by " + byDateTime.format(inputFormatter);
    }
}
  
