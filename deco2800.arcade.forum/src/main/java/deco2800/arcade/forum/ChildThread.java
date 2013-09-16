package deco2800.arcade.forum;

import java.sql.Timestamp;

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
	private User createdBy;
	private Timestamp timestamp;
	private int like;
	
	/**
	 * Constructor: Create ChildThread
	 * 
	 * @param id, a non negative integer of the child thread's id
	 * @param message, string of thread's content
	 * @param createdBy, User Instance
	 * @param timestamp, Timestamp Instance specified in SQL Timestamp
	 * @param like, an incrementing non negative integer
	 * @require params != null, except like
	 * @require id >= 0 
	 */
	public ChildThread(int id, String message, User createdBy, Timestamp timestamp, int like) {
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
	 * @return User instance
	 */
	public User getCreatedBy() {
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
	
//	public getThread() {
		
//	}
	
//	public submitThread() {
		
//	}
	
//	public editThread() {
		
//	}
	
	
	

}
