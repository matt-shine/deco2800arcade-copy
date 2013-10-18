package deco2800.arcade.protocol.forum;

/**
 * Request for parent threads that have a specific tag and conditions.
 * Response.result will be null if no results.<br>
 * If userId != 0, getTaggedParentThreads(tag, userId).<br>
 * If start, end or limit != 0, getTaggedParentThreads(tag,start,end,limit)<br>
 * Otherwise, getTaggedParentThreads(tag).<br>
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.server.database.ForumStorage.getTaggedParentThreads()
 */
public class GetTaggedParentThreadsRequest {
	public String tag;
	public int userId;
	public int start;
	public int end;
	public int limit;
}
