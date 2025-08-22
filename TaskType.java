public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String description;

    TaskType(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }
}
