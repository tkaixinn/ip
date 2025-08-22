import java.io.Serializable;

public class Todo extends Task implements Serializable {

    public Todo(String description) {
	super(description);
    } 

    @Override
    public String toFileString() {
	return "TODO | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
	return "[" + TaskType.TODO.getDescription() + "]" + super.toString();
    }
}
