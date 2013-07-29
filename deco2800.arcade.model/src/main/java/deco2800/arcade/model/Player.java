package deco2800.arcade.model;

import java.util.Set;

public class Player {

	//TODO shared between server & client?
	
	private String username;
	
	private Set<Achievement> achievements;

	public Player(){
		
	}
	/**
	 * Sets the name of the Player
	 * @param username
	 */
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

	/**
	 * Sets the name of the user.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/*
	 * Returns a set listing the achievements of the player.
	 */
	public Set<Achievement> getAchievements() {
		return achievements;
	}

	/**
	 * Sets the supplied achievements for a player.
	 * @param achievements - Achievement to be set.
	 */
	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
	}
	
	
}
