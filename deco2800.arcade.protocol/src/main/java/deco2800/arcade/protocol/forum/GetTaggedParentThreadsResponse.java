package deco2800.arcade.protocol.forum;

import deco2800.arcade.model.forum.ParentThread;

/**
 * Response for GetTaggedParentThreadsRequest
 * result contains array of parent threads
 * error is if give.
 *
 */
public class GetTaggedParentThreadsResponse {
	public ParentThread[] result;
	public String error;
}
