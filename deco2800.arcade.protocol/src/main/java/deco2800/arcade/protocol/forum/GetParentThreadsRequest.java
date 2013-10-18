package deco2800.arcade.protocol.forum;

/**
 * Request for getParentThreads() which returns ParentThread array. <br>
 * If userId > 0, it will call getParentThreads(start, end, limit, userId). <br>
 * If category != "", it will call getParentThreads(start, end, limit, category).<br>
 * Otherwise, it will call getParentThreads(start, end, limit).
 * 
 * @see deco2800.arcade.server.ForumStorage	for response behaviour.
 */
public class GetParentThreadsRequest {
	public int start;
	public int end;
	public int limit;
	public int userId;
	public String category;
}
