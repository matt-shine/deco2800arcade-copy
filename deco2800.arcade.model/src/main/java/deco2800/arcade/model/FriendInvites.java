package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class FriendInvites {
	private Set<User> friendInvites;
	
	public FriendInvites() {
		this.friendInvites = new HashSet<User>();
	}
	
	public FriendInvites(FriendInvites f) {
		this.friendInvites = new HashSet<User>(f.friendInvites);
	}
	
	public Set<User> getSet() {
		return new HashSet<User>(friendInvites);
	}
	
	public void add(User user) {
		this.friendInvites.add(new User(user));
	}
	
	public void remove(User user){
		this.friendInvites.remove(new User(user));
	}
	
	public boolean contains(User user){
		return this.friendInvites.contains(new User(user));
	}
}
