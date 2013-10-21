package deco2800.arcade.protocol.forum;


/**
 * Response for GetTaggedParentThreadsRequest
 * result contains array of parent threads
 * error is if give. result = null if no result.
 *
 * @author Junya, Team Forum
 */
public class GetTaggedParentThreadsResponse {
	public ParentThreadProtocol[] result;
	public String error;
}
