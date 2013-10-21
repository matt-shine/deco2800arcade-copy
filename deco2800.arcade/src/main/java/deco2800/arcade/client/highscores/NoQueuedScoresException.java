package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")
public class NoQueuedScoresException extends RuntimeException {

	public NoQueuedScoresException() {
		
	}
	
	public NoQueuedScoresException(String msg) {
		super(msg);
	}

}
