package deco2800.arcade.protocol.forum;

import deco2800.arcade.protocol.forum.ParentThreadProtocol;;

/**
 * Response to GetParentThreadsRequest
 *
 */
public class GetParentThreadsResponse {
	public ParentThreadProtocol[] result;
	public String error;
}
