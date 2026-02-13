package amia.exception;

/**
 * Custom exception class for the Amia application.
 */
public class AmiaException extends Exception {
    /**
     * Constructs an AmiaException with the given message.
     *
     * @param message The detail message.
     */
    public AmiaException(String message) {
        super(message);
    }
}
