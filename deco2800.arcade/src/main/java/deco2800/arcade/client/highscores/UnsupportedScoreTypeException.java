package deco2800.arcade.client.highscores;

@SuppressWarnings("serial")
public class UnsupportedScoreTypeException extends RuntimeException {

	public UnsupportedScoreTypeException() {
		
	}
	
	public UnsupportedScoreTypeException(String msg) {
		super(msg);
	}

}
