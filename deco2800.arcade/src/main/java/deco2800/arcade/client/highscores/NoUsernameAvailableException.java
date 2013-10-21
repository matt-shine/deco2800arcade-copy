package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")
public class NoUsernameAvailableException extends RuntimeException {

	public NoUsernameAvailableException() {
		
	}
	
	public NoUsernameAvailableException(String msg) {
		super(msg);
	}

}
