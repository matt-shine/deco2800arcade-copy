package deco2800.arcade.protocol.forum;

/**
 * Request for deleting thread having an id.
 * Specify threadType to choose parent or child ("parent" or "child").<br>
 * It is noted that deleting parent thread will delete all underlying child thread.
 * (By ON DELETE CASCADE)
 * 
 * @author Junya, Team-Forum
 */
public class DeleteRequest {
	public int id;
	public String threadType;
}
