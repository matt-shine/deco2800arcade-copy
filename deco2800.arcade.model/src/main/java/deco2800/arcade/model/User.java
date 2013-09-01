package deco2800.arcade.model;

import java.util.Observable;


/**
 * 
 * @author Leggy (Lachlan Healey: lachlan.j.healey@gmail.com)
 *
 */
public class User extends Observable{
	
	
	private int playerID;
	
	public User(){
		
	}

	public User(int id){
		this.playerID = id;
	}
	
	public User(User user) {
		this.playerID = user.playerID;
	}
	
	public User(Player player) {
		this.playerID = player.getID();
	}
	
	/**
	 * Access method for playerID
	 * @return	Returns the playerID
	 */
	public int getID(){
		return playerID;
	}
	
	public String toString(){
		return "" + this.playerID;
	}
	
	public boolean equals(Object o){
		if (o instanceof User){
			return ((User)o).getID() == this.playerID;
		}
		return false;
		
	}
	
	public int hashCode(){
		return this.playerID;
	}
	


}
