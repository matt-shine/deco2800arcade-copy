package deco2800.arcade.client.replay.exception;

@SuppressWarnings("serial")
public class RecordingNotStartedException extends RuntimeException {

    public RecordingNotStartedException() {
    }

    public RecordingNotStartedException(String arg0) {
        super(arg0);
    }

    public RecordingNotStartedException(Throwable arg0) {
        super(arg0);
    }

    public RecordingNotStartedException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
