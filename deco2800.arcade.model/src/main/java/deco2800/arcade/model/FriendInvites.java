package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

/**
 * FriendInvites is an abstraction of a Player's FriendInvites List.
 * 
 * @author Leggy
 * 
 */
public class FriendInvites {

	private Set<User> friendInvites;
	private int updatedID;
	private boolean added;

	/**
	 * Creates a new FriendInvites.
	 */
	public FriendInvites() {
		this.friendInvites = new HashSet<User>();
	}

	/**
	 * Created a new FriendInvites given an existing FriendInvites, copying the
	 * entries.
	 * 
	 * @param f
	 *            The FriendInvites instance to be copied.
	 */
	public FriendInvites(FriendInvites f) {
		this.friendInvites = new HashSet<User>(f.friendInvites);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<User> getSet() {
		return new HashSet<User>(friendInvites);
	}

	/**
	 * Adds a User to this.
	 * 
	 * @param user
	 *            The User to be added.
	 */
	public void add(User user) {
		if (!contains(user)) {
			this.friendInvites.add(new User(user));
			updatedID = user.getID();
			added = true;
		}
	}
	
	/**
	 * Adds a Set of Users to this.
	 * 
	 * @param user
	 *            The Set of Users to be added.
	 */
	public void addAll(Set<User> user) {
		this.friendInvites.addAll(user);
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		if (contains(user)) {
			this.friendInvites.remove(new User(user));
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
		return this.friendInvites.contains(new User(user));
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
