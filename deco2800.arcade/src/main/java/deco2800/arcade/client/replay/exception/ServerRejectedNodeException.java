package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class ServerRejectedNodeException extends RuntimeException {

    public ServerRejectedNodeException() {
    }

    public ServerRejectedNodeException(String message) {
        super(message);
    }

    public ServerRejectedNodeException(Throwable cause) {
        super(cause);
    }

    public ServerRejectedNodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
