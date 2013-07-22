package deco2800.arcade.client;

public class ArcadeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1216089831869884067L;

	public ArcadeException(String message){
		super(message);
	}
	
	public ArcadeException(Throwable cause){
		super(cause);
	}
	
	public ArcadeException(String message, Throwable cause){
		super(message, cause);
	}
}
