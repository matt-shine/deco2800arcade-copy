package deco2800.arcade.model;

import java.util.Set;

/**
 * Blocked is an abstraction of a Player's Blocked List.
 * 
 * @author iamwinrar
 * 
 */
public class Blocked extends UserSet{
	
	/**
	 * Creates a new Blocked.
	 */
	public Blocked() {
		super();
	}

	/**
	 * Created a new Blocked given an existing Blocked, copying the entries.
	 * 
	 * @param b
	 *            The Blocked instance to be copied.
	 */
	public Blocked(Blocked b) {
		super(b);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<User> getSet() {
		return super.getSet();
	}

	/**
	 * Adds a User to this.
	 * 
	 * @param user
	 *            The User to be added.
	 */
	public void add(User user) {
		super.add(user);
	}
	
	/**
	 * Adds a Set of Users to this. THIS METHOD IS NOT TO BE USED FOR PURPOSES
	 * OTHER THAN LOADING PLAYER DATA FROM DATABASE.
	 * 
	 * @param user
	 *            The Set of Users to be added.
	 */
	public void addAll(Set<User> user) {
		super.addAll(user);
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		super.remove(user);
	}

	/**
	 * Checks if a User is in this.
	 * 
	 * @param user
	 *            The User to check.
	 * @return Returns true if the User is in this, false otherwise.
	 */
	public boolean contains(User user) {
		return super.contains(user);
	}
	
	/**
	 * Access method for the most recent change ID.
	 * 
	 * @return Returns the ID of the field in the most recent change.
	 */
	public int getUpdatedID() {
		return super.getUpdatedID();
	}

	/**
	 * Access method for most recent change flag.
	 * 
	 * @return Returns true if the last change was an addition, and false if it
	 *         was a deletion.
	 */
	public boolean getAdded() {
		return super.getAdded();
	}
}
