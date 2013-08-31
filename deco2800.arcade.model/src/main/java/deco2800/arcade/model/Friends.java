package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class Friends {

	private Set<User> friends;
	
	public Friends(){
		this.friends = new HashSet<User>();
	}
	
	public Friends(Friends friends){
		this.friends = new Hashset<User>(friends.getSet);
	}
	
	private Set<User> getSet(){
		return friends;
	}
	
	
	
}
