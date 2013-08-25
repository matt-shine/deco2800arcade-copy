package deco2800.arcade.model;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Player {

	// TODO shared between server & client?
	
	private int playerID;

	private String username;
	
	private Set<Game> games;
	
	private Set<Player> friends;
	
	private Set<Player> blocked; 
	
	private Set<Player> friendInvites;
	
	private Icon icon;
	
	//private String realName;
	
	//private String location;
	
	//private String biography;
	
	//private String onlineStatus;

	public Player() {

	}
	
	public Player(String username) {
		
		this.username = username;
	}

	/**
	 * Creates a new Player given a name, achievement set and icon filename.
	 *
	 * @param playerID
	 *            The Player's nameID
	 * @param username
	 *            The Player's name
	 * @param filepath
	 *            The Player's icon filepath
	 * @throws IOException
	 *             Throws exception when the image cannot be found at the
	 *             designated filepath.
	 */
	public Player(int playerID, String username, String filepath) 
            throws IOException {
		// TODO: Validate username input

		// TODO validate filepath
		
		// TODO validate playerID
		
		this.playerID = playerID;

		this.username = username;
		this.games = new HashSet<Game>();
		this.friends = new HashSet<Player>();
		this.friendInvites = new HashSet<Player>();
		this.blocked = new HashSet<Player>();
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
	

	
	/**
	 * Access method for player's friends list
	 * @return A set containing the player's friends.
	 */
	public Set<Player> getFriends() {
		Set<Player> clone = new HashSet<Player>(this.friends);
		return clone;
	}
	
	/**
	 * Sets the player's friends to the set provided.
	 * 
	 * @param friends
	 * 			A set containing the player's friends.
	 */
	public void setFriends(Set<Player> friends) {
		if (friends != null) {
			this.friends = new HashSet<Player>(friends);
		}
	}
	
	/**
	 * Checks if the player is already friends with the specified player
	 * 
	 * @param friend
	 * 			The player that is being verified as a friend.
	 * @return
	 * 		True if the player is friends with the specified player, 
	 * 		false otherwise.
	 */
	public boolean isFriend(Player player) {
		return this.friends.contains(player);
	}
	
	/**
	 * Adds a friend to the player's friends set.
	 * 
	 * @param friend
	 * 			The friend to be added to the friends set.
	 * @ensure this.friends.contains(friend)
	 */
	public void addFriend(Player friend) {
		if (friend != null && !this.isFriend(friend)) {
			this.friends.add(friend);
		}
		//TODO: throw exception if friend is already in friends list
	}
	
	/**
	 * Remove a friend from the player's friends list.
	 * 
	 * @param friend
	 * 			Friend to be removed.
	 * @ensure !this.friends.contains(friend)
	 */
	public void removeFriend(Player friend) {
		//TODO: add option to add friend to block list
		if (this.isFriend(friend)) {
			this.friends.remove(friend);
		}
		//TODO: throw exception if friend is not in friend's list
	}
	
	/**
	 * Access method for player's invite list.
	 * 
	 * @return A set containing the player's invites.
	 */
	public Set<Player> getInvites() {
		Set<Player> clone = new HashSet<Player>(this.friendInvites);
		return clone;
	}
	
	/**
	 * Sets the player's invites to the set provided.
	 * 
	 * @param invites
	 * 			A set containing the player's invites.
	 */
	public void setInvites(Set<Player> invites) {
		if (invites != null) {
			this.friendInvites = new HashSet<Player>(invites);
		}
	}
	
	/**
	 * Checks if the player is already has an invite from the specified player.
	 * 
	 * @param player
	 * 			The player that is sending the invite.
	 * @return
	 * 			True if the player already has an invite from the specified 
	 * 			player, otherwise false.
	 */
	public boolean hasInvite(Player player) {
		return this.friendInvites.contains(player);
	}
	
	/**
	 * Adds a player to the player's invite set.
	 * 
	 * @param player
	 * 			The player to be added to the player's invite set.
	 * 
	 * @ensure this.friendInvites.contains(player)
	 */
	public void addInvite(Player player) {
		if (player != null && !this.hasInvite(player)) {
			this.friendInvites.add(player);
		}
		//TODO: throw exception if player is already in invites list
	}
	
	/**
	 * Remove an invite from the player's invite list.
	 * 
	 * @param friend
	 * 			Friend to be removed.
	 * @ensure
	 * 			!this.friendInvites.contains(player)
	 */
	public void removeInvite(Player player) {
		//TODO: add option to add friend to block list
		if (this.hasInvite(player)) {
			this.friendInvites.remove(player);
		}
		//TODO: throw exception if player is not in invite list
	}
	
	
	/**
	 * Access method for player's blocked list
	 * @return A set containing the player's blocked list.
	 */
	public Set<Player> getBlockedList() {
		Set<Player> clone = new HashSet<Player>(this.blocked);
		return clone;
	}
	
	/**
	 * Sets the player's blocked list to the set provided.
	 * 
	 * @param blocked
	 * 			A set containing the player's blocked list.
	 */
	public void setBlockedList(Set<Player> blocked) {
		if (blocked != null) {
			this.blocked = new HashSet<Player>(blocked);
		}
	}
	
	/**
	 * Checks if the player has already blocked the specified player
	 * 
	 * @param player
	 * 			The player that is being verified as blocked.
	 * @return
	 * 		True if the player has blocked the specified player, 
	 * 		false otherwise.
	 */
	public boolean isBlocked(Player player) {
		return this.blocked.contains(player);
	}
	
	/**
	 * Adds a player to the player's blocked set.
	 * 
	 * @param player
	 * 			The player to be added to the blocked set.
	 * @ensure this.blocked.contains(player)
	 */
	public void addBlocked(Player player) throws Exception {
		if (player != null) {
			if(this.isBlocked(player)){
				throw new Exception("Player is already blocked"); }
			else {
				this.blocked.add(player); }
		}
	}
	
	/**
	 * Remove a player from the player's blocked list.
	 * 
	 * @param player
	 * 			player to be removed from blocked list.
	 * @ensure !this.blocked.contains(player)
	 */
	public void removeBlocked(Player player) throws Exception {
		if (this.isBlocked(player)) {
			this.blocked.remove(player); }
		else {
			throw new Exception("Player is not in your blocked list"); }
		}
	}
	
