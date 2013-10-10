package deco2800.arcade.model.forum;

/**
 * This models user for Forum (Forum version of Arcade User).
 */
public class ForumUser {
	private int id;
	private String name;
	
	/**
	 * Constructor
	 * 
	 * @param id	int, id of an user.
	 * @param name	String, name (username) of an user.
	 */
	public ForumUser(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Return user's id (ForumUser's property)
	 * 
	 * @return	int, id of an user
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Return user's name (ForumUser's property)
	 * 
	 * @return	String, user's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * String representation of ForumUser in "id: name".
	 */
	@Override
	public String toString() {
		StringBuilder sd = new StringBuilder();
		sd.append(this.id);
		sd.append(": ");
		sd.append(this.name);
		return new String(sd);
	}
}
