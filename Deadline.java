public class Deadline extends Task { 
    public String date;

    public Deadline(String description) {
	super(description.split("/", 2)[0]);
	String byDate = description.split("/", 2)[1];
	String[] splitAgain = byDate.split("\\s", 2);
        this.date = splitAgain[1];
    } 

    @Override 
    public String toString() { 
	return "[" + TaskType.DEADLINE.getDescription() + "]" + super.toString() + "(by: " + date + ")";

    }
}
  
