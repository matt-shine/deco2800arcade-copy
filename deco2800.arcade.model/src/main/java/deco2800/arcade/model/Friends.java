package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class Friends {

	private Set<User> friends;
	
	public Friends(){
		this.friends = new HashSet<User>();
	}
	
	public Friends(Friends f){
		this.friends = new HashSet<User>(f.friends);
	}
	
	public Set<User> getSet(){
		return new HashSet<User>(friends);
	}
	
	public void add(User user){
		this.friends.add(new User(user));
	}
	
	public void remove(User user){
		this.friends.remove(new User(user));
	}
	
	public boolean contains(User user){
		return this.friends.contains(new User(user));
	}
	
	
	
}
