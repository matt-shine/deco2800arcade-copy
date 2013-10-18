package deco2800.arcade.protocol.forum;

/**
 * Response for GetChildThreadsRequest. Generally result = null if 
 * no results (records) are found on Database.
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.server.database.ForumStorage.getChildThreads()
 */
public class GetChildThreadsResponse {
	public ChildThreadProtocol[] result;
	public String error;
}
