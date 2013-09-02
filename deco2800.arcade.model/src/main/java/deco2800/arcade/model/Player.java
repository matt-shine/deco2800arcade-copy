package deco2800.arcade.model;

import java.util.Set;

public class Player extends User {

	public static final int USERNAME_ID = 1;
	public static final int NAME_ID = 2;
	public static final int EMAIL_ID = 3;
	public static final int PROGRAM_ID = 4;
	public static final int BIO_ID = 5;

	// TODO shared between server & client?

	private Field username;

	// TODO Implement these
	private Field name;
	private Field email;
	private Field program;
	private Field bio;

	private Games games;
	private Friends friends;
	private Blocked blocked;
	private FriendInvites friendInvites;

	private Icon icon;

	/**
	 * Creates a new Player given a name, achievement set and icon filename.
	 * 
	 * @param playerID
	 *            The Player's nameID
	 * @param username
	 *            The Player's name
	 * @param filepath
	 *            The Player's icon filepath
	 */
	public Player(int playerID, String username, String filepath) {
		super(playerID);
		this.username = new Field(USERNAME_ID, username);
		this.games = new Games();
		this.friends = new Friends();
		this.friendInvites = new FriendInvites();
		this.blocked = new Blocked();
		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */

		// TODO: UTILISE REVIDES ICON API TO AVOID EXCEPTIONS - DEFUALT TO
		// PLACEHOLDER
		// this.icon = new Icon(filepath);
		/*
		 * @throws IOException Throws exception when the image cannot be found
		 * at the designated filepath.
		 */
		this.icon = null;
	}

	/**
	 * getUsername returns a string of the player created
	 * 
	 * @return a string of the username
	 */
	public String getUsername() {
		return username.getValue();
	}

	/**
	 * Sets the name of the user.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		if (username != null) {
			this.username.setValue(username);
		}
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
		return games.getSet();
	}

	/**
	 * Adds an game to the player's games set.
	 * 
	 * @param game
	 *            The game to be added to the set.
	 * @ensure this.games.contains(game)
	 */
	public void addGame(Game game) {
		if (game != null) {
			this.games.add(game);
			setChanged();
			notifyObservers(games);
			clearChanged();
		}
	}

	/**
	 * Removes a game from the player's games.
	 * 
	 * @param game
	 *            The game to be removed
	 * @ensure !this.games.contains(game)
	 */
	public void removeGame(Game game) {
		this.games.remove(game);
		setChanged();
		notifyObservers(games);
		clearChanged();
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

	/**
	 * Access method for player's friends list
	 * 
	 * @return A set containing the player's friends.
	 */
	public Set<User> getFriends() {
		return friends.getSet();
	}

	/**
	 * Checks if the player is already friends with the specified player
	 * 
	 * @param friend
	 *            The player that is being verified as a friend.
	 * @return True if the player is friends with the specified player, false
	 *         otherwise.
	 */
	public boolean isFriend(User player) {
		return this.friends.contains(player);
	}

	/**
	 * Adds a friend to the player's friends set, given that
	 * player.hasInvite(friend).
	 * 
	 * @param friend
	 *            The friend to be added to the friends set.
	 * @ensure this.friends.contains(friend)
	 */
	public void addFriend(User friend) {
		if (friend != null /*&& this.hasInvite(friend)*/) {
			this.friends.add(friend);
			setChanged();
			notifyObservers(friends);
			clearChanged();
		}
	}

	/**
	 * Remove a friend from the player's friends list.
	 * 
	 * @param friend
	 *            Friend to be removed.
	 * @ensure !this.friends.contains(friend)
	 */
	public void removeFriend(User friend) {
		if (friend != null) {
			this.friends.remove(friend);
			setChanged();
			notifyObservers(friends);
			clearChanged();
		}
	}

	/**
	 * Access method for player's invite list.
	 * 
	 * @return A set containing the player's invites.
	 */
	public Set<User> getInvites() {
		return this.friendInvites.getSet();
	}

	/**
	 * Checks if the player is already has an invite from the specified player.
	 * 
	 * @param player
	 *            The player that is sending the invite.
	 * @return True if the player already has an invite from the specified
	 *         player, otherwise false.
	 */
	public boolean hasInvite(User player) {
		return this.friendInvites.contains(player);
	}

	/**
	 * Adds a player to the player's invite set.
	 * 
	 * @param player
	 *            The player to be added to the player's invite set.
	 * 
	 * @ensure this.friendInvites.contains(player)
	 */
	public void addInvite(User player) {
		if (player != null) {
			this.friendInvites.add(player);
			setChanged();
			notifyObservers(friendInvites);
			clearChanged();
		}
	}

	/**
	 * Remove an invite from the player's invite list.
	 * 
	 * @param friend
	 *            Friend to be removed.
	 * @ensure !this.friendInvites.contains(player)
	 */
	public void removeInvite(User player) {
		if (player != null) {
			this.friendInvites.remove(player);
			setChanged();
			notifyObservers(friendInvites);
			clearChanged();
		}
	}

	/**
	 * Access method for player's blocked list
	 * 
	 * @return A set containing the player's blocked list.
	 */
	public Set<User> getBlockedList() {
		return this.blocked.getSet();
	}

	/**
	 * Checks if the player has already blocked the specified player
	 * 
	 * @param player
	 *            The player that is being verified as blocked.
	 * @return True if the player has blocked the specified player, false
	 *         otherwise.
	 */
	public boolean isBlocked(User player) {
		return this.blocked.contains(player);
	}

	/**
	 * Adds a player to the player's blocked set.
	 * 
	 * @param player
	 *            The player to be added to the blocked set.
	 * @ensure this.blocked.contains(player)
	 */
	public void blockPlayer(User player) {
		if (player != null) {
			this.blocked.add(player);
			setChanged();
			notifyObservers(blocked);
			clearChanged();
		}
	}

	/**
	 * Remove a player from the player's blocked list.
	 * 
	 * @param player
	 *            player to be removed from blocked list.
	 * @ensure !this.blocked.contains(player)
	 */
	public void removeBlocked(User player) {
		if (player != null) {
			this.blocked.remove(player);
			setChanged();
			notifyObservers(blocked);
			clearChanged();
		}
	}
}
