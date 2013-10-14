package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class RecordingException extends RuntimeException {

    public RecordingException() {
    }

    public RecordingException(String message) {
        super(message);
    }

    public RecordingException(Throwable cause) {
        super(cause);
    }

    public RecordingException(String message, Throwable cause) {
        super(message, cause);
    }

}
