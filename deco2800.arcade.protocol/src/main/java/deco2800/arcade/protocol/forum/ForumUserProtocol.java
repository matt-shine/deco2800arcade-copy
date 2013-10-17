package deco2800.arcade.protocol.forum;

import deco2800.arcade.model.forum.ForumUser;

/**
 * Protocol class to be used to communicate ForumUser instance via KryoNet.
 * It is because no-arguments constructor is required for serialization processes.
 * 
 * @author Junya, Team Forum
 *
 */
public class ForumUserProtocol {
	public int id;
	public String name;
	
	public static ForumUser getForumUser(ForumUserProtocol object) {
		return new ForumUser(object.id, object.name);
	}
	
	public static ForumUserProtocol getForumUserProtocol(ForumUser object) {
		ForumUserProtocol result = new ForumUserProtocol();
		result.id = object.getId();
		result.name = object.getName();
		return result;
	}
}
