package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class NoReplayHandlerException extends RuntimeException {

    public NoReplayHandlerException() {
    }

    public NoReplayHandlerException(String arg0) {
        super(arg0);
    }

    public NoReplayHandlerException(Throwable arg0) {
        super(arg0);
    }

    public NoReplayHandlerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
