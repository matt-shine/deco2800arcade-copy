package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")

/**
 * A runtime exception that is thrown by HighscoreClient when a multivalued
 * scores are trying to be sent to the database when none have been queued.
 * 
 * @author TeamA
 */
public class NoQueuedScoresException extends RuntimeException {

	public NoQueuedScoresException() {
		
	}
	
	public NoQueuedScoresException(String msg) {
		super(msg);
	}

}
