package chatterbox;

/**
 * Abstract class representing generic task in Chatterbox
 * All task types (Todo, Deadline, Event) extend this class
 *
 * Features:
 * - Store task description
 * - Track if task is done
 *
 */

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public abstract String toFileString();

    public abstract Task clone();
    /**
     * Creates Task object from line in data file
     * @param line Line from the file representing a task
     * @return A task object or null
     */

    public static Task fromFileString(String line) {
	String[] parts = line.split(" \\| ");
	String type = parts[0];
	boolean done = parts[1].equals("1");
        if (parts.length < 3) {
            System.out.println("Warning: Skipping malformed line: " + line);
            return null;
        }
	switch(type) {
	case "TODO":
		Task todo = new Todo(parts[2]);
		    if (done) {
                todo.markAsDone();
            }
		return todo;
	case "DEADLINE":
        Task deadline = new Deadline(parts[2]);
		    if (done) {
                deadline.markAsDone();
            }
		return deadline;
    case "EVENT":
        Task event = new Event(parts[2], parts[3], parts[4]);
            if (done) {
                event.markAsDone();
            }
        return event;
    default:
        return null;
    }
}


    /**
     * Constructs a Task with given description
     * @param description Task's description
     */

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks task as done
     */

    public void markAsDone() {
        isDone = true;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }     
}   
 
