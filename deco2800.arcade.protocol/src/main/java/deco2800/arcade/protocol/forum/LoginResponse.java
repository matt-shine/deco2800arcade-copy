package deco2800.arcade.protocol.forum;

/**
 * Protocol for login response
 * 
 * @see deco2800.server.listener.ForumListener
 */
public class LoginResponse {
	/* If result == true, the user is registered in the server */
	public boolean result;
	public String error;
}
