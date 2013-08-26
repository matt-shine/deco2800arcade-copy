package deco2800.arcade.model;

import java.util.Observable;


/**
 * 
 * @author Leggy (Lachlan Healey: lachlan.j.healey@gmail.com)
 *
 */
public class User extends Observable{
	
	
	private int playerID;

	public User(int id){
		this.playerID = id;
	}
	
	public int getID(){
		return playerID;
	}

}
