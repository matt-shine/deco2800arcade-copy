package deco2800.arcade.model.forum;

import java.sql.Timestamp;

import deco2800.arcade.model.User;

/**
 * Child thread models the child thread (sub-thread of the parent thread)
 */
public class ChildThread {
	/**
	 * TODO Complete Javadoc
	 * TODO Implement input validation
	 */

	/* Fields */
	private int id;
	private String message;
	private ForumUser createdBy;
	private Timestamp timestamp;
	private int like;
	
	/**
	 * Constructor: Create ChildThread
	 * 
	 * @param id, a non negative integer of the child thread's id
	 * @param message, string of thread's content
	 * @param createdBy, ForumUser Instance
	 * @param timestamp, Timestamp Instance specified in SQL Timestamp
	 * @param like, an incrementing non negative integer
	 * @require params != null, except like
	 * @require id >= 0 
	 */
	public ChildThread(int id, String message, ForumUser createdBy, Timestamp timestamp, int like) {
		this.id = id;
		this.message = message;
		this.createdBy = createdBy;
		this.timestamp = timestamp;
		this.like = like;
	} 
	
	/**
	 * Return the Id of the child thread
	 * @return int, child thread id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Return the message of the child thread
	 * @return string, child thread's message
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Return the user of the created thread
	 * @return Player instance
	 */
	public ForumUser getCreatedBy() {
		return this.createdBy;
	}
	
	/**
	 * Return timestamp when the child thread is created.
	 * @return Timestamp
	 */
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Return like
	 * @return like	
	 */
	public int getLike() {
		return this.like;
	}
	
	@Override
	public String toString() {
		StringBuilder sd = new StringBuilder();
		sd.append(this.id);
		sd.append(": ");
		sd.append(this.message);
		sd.append(", created by ");
		sd.append(this.createdBy.toString());
		sd.append(", on ");
		sd.append(this.timestamp.toString());
		sd.append(", vote=");
		sd.append(this.like);
		return new String(sd);
	}
//	public getThread() {
		
//	}
	
//	public submitThread() {
		
//	}
	
//	public editThread() {
		
//	}
	
	
	

}
