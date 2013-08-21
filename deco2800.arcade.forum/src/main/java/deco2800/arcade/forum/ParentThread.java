package deco2800.arcade.forum;

import java.sql.Timestamp;

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
	private String tags;
	
	/**
	 * Constructor:
	 */
	public ParentThread(int id, String topic, String message, User createdBy, Timestamp timestamp, String category, String tags) {
		this.id = id;
		this.topic = topic;
		this.message = message;
		this.createdBy = createdBy;
		this.timestamp = timestamp;
		this.category = category;
		this.tags = tags;
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
	 */
	public String getTopic() {
		return this.topic;
	}
	
	/**
	 * Return message of parent thread
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Return User instance who created thread
	 */
	public User getCreatedBy() {
		return this.createdBy;
	}
	
	/** 
	 * Return timestamp when this thread is created
	 */
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Return category which this thread is selected
	 */
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * Return tags which this thread is attached to
	 */
	public String getTags() {
		return this.tags;
	}
}
