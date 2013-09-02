package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Blocked is an abstraction of a Player's Blocked List.
 * 
 * @author iamwinrar
 * 
 */
public class Blocked {
	private Set<User> blocked;
	private int updatedID;
	private boolean added;

	/**
	 * Creates a new Blocked.
	 */
	public Blocked() {
		this.blocked = new HashSet<User>();
	}

	/**
	 * Created a new Blocked given an existing Blocked, copying the entries.
	 * 
	 * @param b
	 *            The Blocked instance to be copied.
	 */
	public Blocked(Blocked b) {
		this.blocked = new HashSet<User>(b.blocked);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<User> getSet() {
		return new HashSet<User>(blocked);
	}

	/**
	 * Adds a User to this.
	 * 
	 * @param user
	 *            The User to be added.
	 */
	public void add(User user) {
		if (!contains(user)) {
			this.blocked.add(new User(user));
			updatedID = user.getID();
			added = true;
		}
	}

	/**
	 * Removes a User from this.
	 * 
	 * @param user
	 *            The User to be removed.
	 */
	public void remove(User user) {
		if (!contains(user)) {
			this.blocked.remove(new User(user));
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
		return this.blocked.contains(new User(user));
	}
}
