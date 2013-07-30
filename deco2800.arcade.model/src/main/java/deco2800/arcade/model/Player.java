package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

public class Player {

	// TODO shared between server & client?

	// TODO player icons

	private String username;

	private Set<Achievement> achievements;

	public Player() {

	}

	/**
	 * Sets the name of the Player
	 * 
	 * @param username
	 */
	public Player(String username) {
		this.username = username;
	}

	/**
	 * getUsername returns a string of the player created
	 * 
	 * @return a string of the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the name of the user.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Access method for player's achievements.
	 * 
	 * @return Returns a set containing the player's Achievements.
	 */
	public Set<Achievement> getAchievements() {
		/*
		 * Cloning this.achievements to preserve immutability
		 */
		Set<Achievement> clone = new HashSet<Achievement>(this.achievements);
		return clone;
	}

	/**
	 * Sets the supplied achievements for a player.
	 * 
	 * @param achievements
	 *            - Achievement to be set.
	 */
	public void setAchievements(Set<Achievement> achievements) {
		/*
		 * Preserving immutability
		 */
		this.achievements = new HashSet<Achievement>(achievements);
	}

	/**
	 * Adds an achievement to the player's achievements.
	 * 
	 * @param achievement
	 *            The achievement to be added.
	 */
	public void addAchievement(Achievement achievement) {
		this.achievements.add(new Achievement(achievement));
	}

	/**
	 * Removes and achievement from the player's achievements.
	 * 
	 * @param achievement
	 *            The achievement to be removed
	 */
	public void removeAchievement(Achievement achievement) {
		this.achievements.remove(achievement);
	}

	/**
	 * Checks if the player has an Achievement.
	 * 
	 * @param achievement
	 *            The achievement to be checked.
	 * @return Returns true if player has specified achievement, false
	 *         otherwise.
	 */
	public boolean hasAchievement(Achievement achievement) {
		return this.achievements.contains(achievement);
	}

}
