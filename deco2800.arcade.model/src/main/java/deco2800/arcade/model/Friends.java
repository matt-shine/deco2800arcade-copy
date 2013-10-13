package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Friends is an abstraction of a Player's Friends List.
 * 
 * @author Leggy
 * 
 */
public class Friends {

	private Set<User> friends;
	private int updatedID;
	private boolean added;

	/**
	 * Creates a new Friends.
	 */
	public Friends() {
		this.friends = new HashSet<User>();
	}

	/**
	 * Created a new Friends given an existing Friends, copying the entries.
	 * 
	 * @param f
	 *            The Friends instance to be copied.
	 */
	public Friends(Friends f) {
		this.friends = new HashSet<User>(f.friends);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<User> getSet() {
		return new HashSet<User>(friends);
	}

	/**
	 * Adds a User to this.
	 * 
	 * @param user
	 *            The User to be added.
	 */
	public void add(User user) {
		if (!contains(user)) {
			this.friends.add(new User(user));
			updatedID = user.getID();
			added = true;
		}
	}

	/**
	 * Adds a Set of Users to this. THIS METHOD IS NOT TO BE USED FOR PURPOSES
	 * OTHER THAN LOADING PLAYER DATA FROM DATABASE.
	 * 
	 * @param user
	 *            The Set of Users to be added.
	 */
	public void addAll(Set<User> user) {
		this.friends.addAll(user);
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		if (contains(user)) {
			this.friends.remove(new User(user));
			updatedID = user.getID();
			added = false;
		}
	}

	/**
	 * Checks if a User is in this.
	 * 
	 * @param user
	 *            The User to check.
	 * @return Returns true if the User is in this, false otherwise.
	 */
	public boolean contains(User user) {
		return this.friends.contains(new User(user));
	}

	/**
	 * Access method for the most recent change ID.
	 * 
	 * @return Returns the ID of the field in the most recent change.
	 */
	public int getUpdatedID() {
		return updatedID;
	}

	/**
	 * Access method for most recent change flag.
	 * 
	 * @return Returns true if the last change was an addition, and false if it
	 *         was a deletion.
	 */
	public boolean getAdded() {
		return added;
	}
}
