package deco2800.arcade.client.network;

public class NetworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6833940937932739053L;

	public NetworkException(String message){
		super(message);
	}
	
	public NetworkException(Throwable cause){
		super(cause);
	}
	
	public NetworkException(String message, Throwable cause){
		super(message, cause);
	}
}
