package chatterbox;

/**
 * Represents type of task in system
 * Each type has short string representation
 */

public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String description;

    /**
     * Constructs TaskType with string description
     * @param description
     */

    TaskType(String description) {
        this.description = description;
    }

    public String getDescription() {
	    return description;
    }
}
