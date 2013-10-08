package deco2800.arcade.model.forum;

/**
 * This models user for Forum (Forum version of Arcade User).
 *
 */
public class ForumUser {
	private int id;
	private String name;
	
	public ForumUser(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		StringBuilder sd = new StringBuilder();
		sd.append(this.id);
		sd.append(": ");
		sd.append(this.name);
		return new String(sd);
	}
}
