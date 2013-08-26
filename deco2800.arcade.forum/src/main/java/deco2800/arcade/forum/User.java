package deco2800.arcade.forum;

/** 
 * User models user or player of the arcade.
 * Note: We may replace this to the similar class made by others when integrating.
 * 
 * @author Team-Forum
 *
 */
public class User {
	/**
	 * TODO Add javadoc
	 * TODO Consider whether to use this class or replace later. 
	 */
	/* Fields */
	private int id;
	private String name;
	
	/**
	 * Constructor:
	 */
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Return id of user.
	 */
	public int getId() {
		return this.id;
	}
	
	/** 
	 * Return string of user
	 */
	public String getName() {
		return this.name;
	}
}
