package deco2800.arcade.protocol.forum;

import java.sql.Timestamp;
import java.util.ArrayList;

import deco2800.arcade.model.forum.ChildThread;

/**
 * Protocol class designed for communicating ChildThread instance. 
 * Use this instead of ChildThread if you want communicate over the KryoNet network. 
 * Converting functions are given in here.
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.model.forum.ChildThread
 */
public class ChildThreadProtocol {
	public int id;
	public String message;
	public ForumUserProtocol createdBy;
	public Timestamp timestamp;
	public int vote;
	
	public static ChildThreadProtocol getChildThreadProtocol(ChildThread thread) {
		ChildThreadProtocol result = new ChildThreadProtocol();
		result.id = thread.getId();
		result.createdBy = ForumUserProtocol.getForumUserProtocol(thread.getCreatedBy());
		result.message = thread.getMessage();
		result.timestamp = thread.getTimestamp();
		result.vote = thread.getLike();
		return result;
	}
	
	public static ChildThread getChildThread(ChildThreadProtocol object) {
		return new ChildThread(object.id, object.message, ForumUserProtocol.getForumUser(object.createdBy), object.timestamp, object.vote);
	}
	
	public static ChildThreadProtocol[] getChildThreadProtocols(ChildThread[] threads) {
		if (threads == null) {
			return null;
		}
		ArrayList<ChildThreadProtocol> result = new ArrayList<ChildThreadProtocol>();
		for (ChildThread thread : threads) {
			ChildThreadProtocol object = ChildThreadProtocol.getChildThreadProtocol(thread);
			result.add(object);
		}
		return result.toArray(new ChildThreadProtocol[0]);
	}
	
	public static ChildThread[] getChildThreads(ChildThreadProtocol[] threads) {
		if (threads == null) {
			return null;
		}
		ArrayList<ChildThread> result = new ArrayList<ChildThread>();
		for (ChildThreadProtocol thread : threads) {
			ChildThread object = ChildThreadProtocol.getChildThread(thread);
			result.add(object);
		}
		return result.toArray(new ChildThread[0]);
	}
}
