package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class PlaybackException extends RuntimeException {

    public PlaybackException() {
    }

    public PlaybackException(String message) {
        super(message);
    }

    public PlaybackException(Throwable cause) {
        super(cause);
    }

    public PlaybackException(String message, Throwable cause) {
        super(message, cause);
    }

}
