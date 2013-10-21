package deco2800.arcade.protocol.forum;

/**
 * Request for updating child thread data. Only message attr 
 * is updatable.
 * 
 * @author Junya, Team Forum
 */
public class UpdateChildThreadRequest {
	public int cid;
	public String newMessage;
}
