package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class UserSet {
	private Set<User> userSet;
	private int updatedID;
	private boolean added;

	/**
	 * Creates a new UserSet.
	 */
	public UserSet() {
		this.userSet = new HashSet<User>();
	}

	/**
	 * Created a new UserSet given an existing UserSet, copying the
	 * entries.
	 * 
	 * @param f
	 *            The FriendInvites instance to be copied.
	 */
	public UserSet(UserSet f) {
		this.userSet = new HashSet<User>(f.userSet);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<User> getSet() {
		return new HashSet<User>(userSet);
	}

	/**
	 * Adds a User to this.
	 * 
	 * @param user
	 *            The User to be added.
	 */
	public void add(User user) {
		if (!contains(user)) {
			this.userSet.add(new User(user));
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
		this.userSet.addAll(user);
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		if (contains(user)) {
			this.userSet.remove(new User(user));
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
		return this.userSet.contains(new User(user));
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
