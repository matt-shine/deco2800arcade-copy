package deco2800.arcade.replay.exception;

@SuppressWarnings("serial")
public class ReplayItemDataInvalidException extends RuntimeException {
	
    public ReplayItemDataInvalidException() {

    }

    public ReplayItemDataInvalidException(String message) {
        super(message);
    }

    public ReplayItemDataInvalidException(Throwable cause) {
        super(cause);
    }

    public ReplayItemDataInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

}
