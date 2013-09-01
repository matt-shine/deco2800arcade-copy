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
		this.friendInvites.add(new User(user));
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		this.friendInvites.remove(new User(user));
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
}
