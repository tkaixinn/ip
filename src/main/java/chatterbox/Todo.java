package chatterbox;

import java.io.Serializable;

/**
 * Represents a Todo Task
 * Todo is a task without a specified date or time
 */

public class Todo extends Task implements Serializable {

    /**
     * Constructs a new Todo with given description
     * @param description description of Todo Task
     */

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
