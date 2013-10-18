package deco2800.arcade.protocol.forum;

/**
 * Request for getChildThreads() which retrieves child threads satisfying 
 * certain conditions. <br>
 * start, end, limit, userId are optional. If not specifying, set 0 for all.<br>
 * At least pid must be set to non-negative int.
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.server.database.ForumStorage.getChildThreads()
 */
public class GetChildThreadsRequest {
	public int pid;
	public int start;
	public int end;
	public int limit;
	public int userId;
}
