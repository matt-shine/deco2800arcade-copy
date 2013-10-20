package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class DeprecatedMethodException extends RuntimeException {

    public DeprecatedMethodException() {
    }

    public DeprecatedMethodException(String message) {
        super(message);
    }

    public DeprecatedMethodException(Throwable cause) {
        super(cause);
    }

    public DeprecatedMethodException(String message, Throwable cause) {
        super(message, cause);
    }

}
