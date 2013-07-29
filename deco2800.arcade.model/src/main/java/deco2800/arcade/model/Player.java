package deco2800.arcade.model;

import java.util.Set;

public class Player {

	//TODO shared between server & client?
	
	private String username;
	
	private Set<Achievement> achievements;

	public Player(){
		
	}
	
	public Player(String username) {
		this.username = username;
	}

	/**
	 * getUsername returns a string of the player created 
	 * @return a string of the username 
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
	}
	
	
}
