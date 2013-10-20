package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")

/**
 * A runtime exception that is thrown by HighscoreClient when a score of an
 * unsupported type is about to be added to the database.
 * 
 * @author TeamA
 */
public class UnsupportedScoreTypeException extends RuntimeException {

	public UnsupportedScoreTypeException() {
		
	}
	
	public UnsupportedScoreTypeException(String msg) {
		super(msg);
	}

}
