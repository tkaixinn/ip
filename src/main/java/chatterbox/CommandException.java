package chatterbox;

/**
 * Constructs a new Command Exception with message
 */

public class CommandException extends RuntimeException {
    public CommandException(String message) {
        super(message);
    }
}

