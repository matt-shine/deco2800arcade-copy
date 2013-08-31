package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class Blocked {
	private Set<User> blocked;
	
	public Blocked() {
		this.blocked = new HashSet<User>();
	}
	
	public Blocked(Blocked b) {
		this.blocked = new HashSet<User>(b.blocked);
	}
	
	public Set<User> getSet() {
		return new HashSet<User>(blocked);
	}
	
	public void add(User user) {
		this.blocked.add(new User(user));
	}
	
	public void remove(User user) {
		this.blocked.remove(new User(user));
	}
	
	public boolean contains(User user) {
		return this.blocked.contains(new User(user));
	}
}
