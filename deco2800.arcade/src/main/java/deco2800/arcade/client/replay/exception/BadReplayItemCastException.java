package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class BadReplayItemCastException extends RuntimeException {

    public BadReplayItemCastException() {
    }

    public BadReplayItemCastException(String message) {
        super(message);
    }

    public BadReplayItemCastException(Throwable cause) {
        super(cause);
    }

    public BadReplayItemCastException(String message, Throwable cause) {
        super(message, cause);
    }

}
