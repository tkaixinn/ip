public class Event extends Task { 
    String fromPart;
    String toPart;

    public Event(String description, String fromPart, String toPart) { 
	super(description);
	
	this.fromPart = fromPart.split("\\s", 2)[1];
	this.toPart = toPart.split("\\s", 2)[1];
    }

    @Override
    public String toString() {
	return "[E] " + super.toString() + " (from: "+ fromPart + " to: " + toPart + ")";
    }	
}

