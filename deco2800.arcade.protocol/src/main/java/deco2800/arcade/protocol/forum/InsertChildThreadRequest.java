package deco2800.arcade.protocol.forum;

/**
 * Request for inserting child thread. Make sure that 
 * pThread (id of parent thread which hold a new child thread) should
 * be valid (i.e. exist in DB).
 * 
 * @author Junya, Team-Forum
 *
 */
public class InsertChildThreadRequest {
	public String message;
	public int createdBy;
	public int pThread;
}
