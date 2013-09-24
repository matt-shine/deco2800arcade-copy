package deco2800.arcade.model.forum;

import java.sql.Timestamp;

import deco2800.arcade.model.User;

/**
 * ParentThread models the parent thread (top-level thread). 
 * 
 * @author 
 */
public class ParentThread {
	/**
	 * TODO Complete Javadoc
	 * TODO Implement input validation
	 * TODO Implement interface class for ParentThread and ChildThread
	 * TODO Create static method to create the ParentThread instance
	 */
	
	/* Fields */
	private int id;
	private String topic;
	private String message;
	private User createdBy;
	private Timestamp timestamp;
	private String category;
	private String[] tags;
	
	/**
	 * Constructor: Create ParentThread
	 * 
	 * @param	id, non-negative integer of parent thread's id 
	 * @param	topic, string of thread's topic which is less than 80 chars
	 * @param	message, string of message (thread's content)
	 * @param	createdBy, Player instance
	 * @param	timestamp, Timestamp instance specified in SQL timestamp
	 * @param	category, string of category which a parent thread is categorised into.
	 * @param	tags, array of string of tags which are attached to the parent thread used for additional search.
	 * @require	params != null except tags, id >= 0,  
	 * @see	java.sql.Timestamp
	 */
	public ParentThread(int id, String topic, String message,
			User createdBy, Timestamp timestamp, String category,
			String tagString) {
		this.id = id;
		this.topic = topic;
		this.message = message;
		this.createdBy = createdBy;
		this.timestamp = timestamp;
		this.category = category;
		this.tags = tagsStrToArray(tagString);
	}
	
	/**
	 * Return id of parent thread
	 * 
	 * @return	int, parent thread id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Return topic of parent thread
	 * 
	 * @return	string, parent thread's topic
	 */
	public String getTopic() {
		return this.topic;
	}
	
	/**
	 * Return message of parent thread
	 * 
	 * @return	string, parent thread's message
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Return User instance who created thread
	 * 
	 * @return	User, user who created the parent thread
	 */
	public User getCreatedBy() {
		return this.createdBy;
	}
	
	/** 
	 * Return timestamp when this thread is created
	 * 
	 * @return	Timestamp, timestamp when the parent thread is created, and timestamp follows the SQL standard.
	 */
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Return category which this thread is selected
	 * 
	 * @return	string, category of parent thread
	 */
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * Return tags which this thread is attached to.
	 * 
	 * @return	string[], tags of parent thread. 
	 */
	public String[] getTags() {
		return this.tags;
	}
	
	public String[] tagsStrToArray(String message) {
		String[] list;
		list = message.split("#");
		for (String s: list){
			s.trim();
		}
		return list;
	}
	/**
	 * Return string of tags which is separated by ';'.
	 * Note; getTags() returns string array.
	 * 
	 * @return string, tags with ';' as separator.
	 */
	public String getTagsString() {
		String result = "";
		for (String s: this.tags) {
			result.concat(s);
			result.concat("#");
		}
		return result;
	}
	
	@Override
	/**
	 * Return human-readable string representation of ParentThread instance
	 * 
	 * @return	string
	 */
	public String toString() {
		return String.format("%d: %s, %s createdBy %s on %s as %s as %s"
				, this.id, this.topic, this.message, this.createdBy.toString()
				, this.timestamp.toString(), this.category, this.getTagsString());
	}
	
	@Override
	/**
	 * Return true if the parameter object is equivalent to this.instance
	 * 
	 * @return	boolean, true if equals
	 */
	public boolean equals(Object pThread) {
		if (pThread == null) {
			return false;
		}
		if (!(pThread instanceof ParentThread)) {
			return false;
		}
		if (!(this.toString().equals(((ParentThread)pThread).toString()))) {
			return false;
		}
		
		return true;
	}

	/**
	 * Create a parent thread from DB retrieved data
	 */
	public static ParentThread getParentThread(int id) {
		ParentThread result = null;
		
		return result;
	}
}
