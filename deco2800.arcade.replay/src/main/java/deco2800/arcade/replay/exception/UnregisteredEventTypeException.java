package deco2800.arcade.replay.exception;

@SuppressWarnings("serial")
public class UnregisteredEventTypeException extends RuntimeException {

    public UnregisteredEventTypeException() {
        
    }

    public UnregisteredEventTypeException(String arg0) {
        super(arg0);
    }

    public UnregisteredEventTypeException(Throwable arg0) {
        super(arg0);
    }

    public UnregisteredEventTypeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
