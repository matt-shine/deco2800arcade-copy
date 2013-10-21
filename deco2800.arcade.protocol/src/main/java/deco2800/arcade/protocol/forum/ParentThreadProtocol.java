package deco2800.arcade.protocol.forum;

import java.sql.Timestamp;
import java.util.ArrayList;

import deco2800.arcade.model.forum.ForumUser;
import deco2800.arcade.model.forum.ParentThread;

/**
 * Protocol class designed for communicating ParentThread instance. 
 * Use this instead of ParentThread if you want communicate over the KryoNet network. 
 * Converting functions are given in here.
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.model.forum.ParentThread
 */
public class ParentThreadProtocol {
	public int id;
	public String topic;
	public String message;
	public ForumUserProtocol createdBy;
	public String timestamp;
	public String category;
	public String[] tags;
	public int vote;
	
	public static ParentThreadProtocol getParentThreadProtocol(ParentThread thread) {
		ParentThreadProtocol result = new ParentThreadProtocol();
		result.id = thread.getId();
		result.topic = thread.getTopic();
		result.message = thread.getMessage();
		result.createdBy = ForumUserProtocol.getForumUserProtocol(thread.getCreatedBy());
		result.timestamp = thread.getTimestamp().toString();
		result.category = thread.getCategory();
		result.tags = thread.getTags();
		result.vote = thread.getVote();
		return result;
	}
	
	public static ParentThread getParentThread(ParentThreadProtocol object) {
		return new ParentThread(object.id, object.topic, object.message, ForumUserProtocol.getForumUser(object.createdBy)
				, Timestamp.valueOf(object.timestamp), object.category, getTagString(object.tags), object.vote);
	}
	
	public static String getTagString(String[] tagArray) {
		StringBuilder sd = new StringBuilder();
		for (int i = 0; i < tagArray.length; i++) {
			sd.append(tagArray[i]);
			if (i != tagArray.length - 1) {
				sd.append("#");
			}
		}
		return new String(sd);
	}
	
	public static ParentThreadProtocol[] getParentThreadProtocols(ParentThread[] threads) {
		if (threads == null) {
			return null;
		}
		ArrayList<ParentThreadProtocol> list = new ArrayList<ParentThreadProtocol>();
		for (ParentThread thread : threads) {
			list.add(ParentThreadProtocol.getParentThreadProtocol(thread));
		}
		return list.toArray(new ParentThreadProtocol[0]);
	}
	
	public static ParentThread[] getParentThreads(ParentThreadProtocol[] objs) {
		if (objs == null) {
			return null;
		}
		ArrayList<ParentThread> list = new ArrayList<ParentThread>();
		for (ParentThreadProtocol obj : objs) {
			list.add(ParentThreadProtocol.getParentThread(obj));
		}
		return list.toArray(new ParentThread[0]);
	}
}
