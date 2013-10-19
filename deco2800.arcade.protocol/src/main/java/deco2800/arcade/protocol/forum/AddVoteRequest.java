package deco2800.arcade.protocol.forum;

/**
 * Request for adding value to vote attr of parent and child thread.
 * value is amount that you want vote (can be negative), threadType is 
 * where parent or child, and id is id of thread (non-zero and non-negative.)
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.server.database.ForumStorage.addVote()
 */
public class AddVoteRequest {
	public int value;
	public String threadType;
	public int id;
}
