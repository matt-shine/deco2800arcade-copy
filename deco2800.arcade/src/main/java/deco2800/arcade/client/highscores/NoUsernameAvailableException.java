package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")

/**
 * A runtime exception that is thrown by HighscoreClient when a method that
 * required a username it run when HighscoreClient is instantiated without a
 * username.
 * 
 * @author TeamA
 */
public class NoUsernameAvailableException extends RuntimeException {

	public NoUsernameAvailableException() {
		
	}
	
	public NoUsernameAvailableException(String msg) {
		super(msg);
	}

}
