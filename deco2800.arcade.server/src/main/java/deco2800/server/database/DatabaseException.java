package deco2800.server.database;

public class DatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1169280146034200425L;

	public DatabaseException(String message){
		super(message);
	}
	
	public DatabaseException(Throwable cause){
		super(cause);
	}
	
	public DatabaseException(String message, Throwable cause){
		super(message, cause);
	}
}
