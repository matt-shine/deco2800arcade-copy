package deco2800.arcade.client;

/**
 * An <tt>ArcadeException</tt> is thrown when there is a problem
 * running the Arcade client (e.g. a network connection error)
 * 
 */
public class ArcadeException extends Exception {

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
