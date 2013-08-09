package deco2800.arcade.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Player {

	// TODO shared between server & client?
	
	private int playerID;

	private String username;

	private Set<Achievement> achievements;
	
	private Set<Game> games;
	
	private Icon icon;

	public Player() {

	}

	@Deprecated
	/**
	 * Sets the name of the Player
	 * 
	 * @param username
	 */
	public Player(String username) {
		/*
		 * Do we want this to be mutable? If so we're going to want to have some
		 * form of immutable playerID.
		 */
		this.username = username;
	}

	/**
	 * Creates a new Player given a name, achievement set and icon filename.
	 *
	 * @param playerID
	 *            The Player's nameID
	 * @param username
	 *            The Player's name
	 * @param achievments
	 *            The Player's achievements
	 * @param filepath
	 *            The Player's icon filepath
	 * @throws IOException
	 *             Throws exception when the image cannot be found at the
	 *             designated filepath.
	 */
	public Player(int playerID, String username, Set<Achievement> achievements,
			String filepath) throws IOException {
		// TODO: Validate username input

		// TODO validate filepath

		// TODO validate achievements set
		
		// TODO validate playerID
		
		this.playerID = playerID;

		this.username = username;
		this.achievements = new HashSet<Achievement>(achievements);
		this.games = new HashSet<Game>();
		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */
		this.icon = new Icon(filepath);
	}
	
	/**
	 * Access method for  playerID
	 * @return	Returns the playerID
	 */
	public int getPlayerID(){
		return this.playerID;
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
		//TODO Validate
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
	 * Sets the Player's achievements to those supplied
	 * 
	 * @param achievements
	 *            The player's achievements.
	 */
	public void setAchievements(Set<Achievement> achievements) {
		/* TODO Validate not null
		 * Preserving immutability
		 */
		this.achievements = new HashSet<Achievement>(achievements);
	}

	/**
	 * Adds an achievement to the player's achievements.
	 * 
	 * @param achievement
	 *            The achievement to be added.
	 * @ensure this.achievement.contains(achievement)
	 */
	public void addAchievement(Achievement achievement) {
		//TODO Validate not null
		this.achievements.add(new Achievement(achievement));
	}

	/**
	 * Removes and achievement from the player's achievements.
	 * 
	 * @param achievement
	 *            The achievement to be removed
	 * @ensure !this.achievement.contains(achievement)
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

	/**
	 * Access method for the Player's icon
	 * 
	 * @return The Player's icon
	 */
	public Icon getIcon() {
		return this.icon.clone();
	}

	/**
	 * Sets the Player's icon that the provided icon.
	 * 
	 * @param icon
	 *            The icon to set to the Player.
	 * @require icon != null
	 */
	public void setIcon(Icon icon) {
		this.icon = icon.clone();
	}
	
	/**
	 * Access method for player's Games set.
	 * 
	 * @return Returns a set containing the player's games.
	 */
	public Set<Game> getGames() {
		/*
		 * Cloning this.games to preserve immutability
		 */
		Set<Game> clone = new HashSet<Game>(this.games);
		return clone;
	} 
	
	/**
	 * Sets the Player's games to the set provided
	 * 
	 * @param games
	 *            The player's games.
	 */
	public void setGames(Set<Game> games) {
		/* 
		 * Preserving immutability
		 */
		if (games != null) {
			this.games = new HashSet<Game>(games);
		}
	}

	/**
	 * Adds an game to the player's games set.
	 * 
	 * @param game
	 *            The game to be added to the set.
	 * @ensure this.games.contains(game)
	 */
	public void addGame(Game game) {
		if (game != null && !this.hasGame(game)) {
			this.games.add(game);
		}
		/*TODO Throw exception if game already in game set */
	}

	/**
	 * Removes a game from the player's games.
	 * 
	 * @param game
	 *            The game to be removed
	 * @ensure !this.games.contains(game)
	 */
	public void removeGame(Game game) {
		if (this.hasGame(game)) {
			this.games.remove(game);
		}
		/*TODO throw exception if game doesn't exist*/
	}

	/**
	 * Checks if the player has a game in their set.
	 * 
	 * @param game
	 *            The game which is being checked
	 * @return Returns true if player has the specified game and false
	 *         otherwise.
	 */
	public boolean hasGame(Game game) {
		return this.games.contains(game);
	}
}
