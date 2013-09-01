package deco2800.server.database;

/**
 * PlayerDatabaseMangager is the interface through which the Server's Listeners
 * update the Player database fields.
 * 
 * @author Leggy
 * 
 */
public class PlayerDatabaseManager {
	private PlayerStorage playerStorage;
	private FriendStorage friendStorage;
	private PlayerGameStorage playerGameStorage;
	private PlayerPrivacy playerPrivacy;
	
	
	public PlayerDatabaseManager(){
		this.playerStorage = new PlayerStorage();
		this.friendStorage = new FriendStorage();
		this.playerGameStorage = new PlayerGameStorage();
		this.playerPrivacy = new PlayerPrivacy();
		
		
		
	}

	/**
	 * Updates the players username to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param username
	 *            The Player's new username.
	 * @require Username is valid, that is it obeys all restrictions imposed by
	 *          the Player class.
	 */
	public void updateUsername(int playerID, String username) {

	}

	/**
	 * Updates the players bio to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param bio
	 *            The Player's new bio.
	 * @require Bio is valid, that is it obeys all restrictions imposed by the
	 *          Player class.
	 */
	public void updateBio(int playerID, String bio) {

	}

	/**
	 * Updates the players email to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param email
	 *            The Player's new email.
	 * @require Email is valid, that is it obeys all restrictions imposed by the
	 *          Player class.
	 */
	public void updateEmail(int playerID, String email) {

	}

	/**
	 * Updates the players program to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param program
	 *            The Player's new program.
	 * @require Program is valid, that is it obeys all restrictions imposed by
	 *          the Player class.
	 */
	public void updateProgram(int playerID, String program) {

	}

	/**
	 * Updates the players name to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param name
	 *            The Player's new name.
	 * @require Name is valid, that is it obeys all restrictions imposed by the
	 *          Player class.
	 */
	public void updateName(int playerID, String name) {

	}

	/**
	 * Adds a friend to a Player's friends list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param friendID
	 *            The friend's playerID.
	 * @require Player and Friend are not already friends.
	 */
	public void addFriend(int playerID, int friendID) {

	}

	/**
	 * Removes a friend from a Player's friends list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param friendID
	 *            The friend's playerID.
	 * @require Player and Friend are friends.
	 */
	public void removeFriend(int playerID, int friendID) {

	}

	/**
	 * Adds a Player (playerID2) to a Player's (playerID1) blocked list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param playerID2
	 *            The blocked Player's playerID.
	 * @require Player does not already have Player (playerID2) blocked.
	 */
	public void addBlocked(int playerID, int playerID2) {

	}

	/**
	 * Removes a Player (playerID2) from a Player's (playerID1) blocked list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param playerID2
	 *            The blocked Player's playerID.
	 * @require Player already has Player (playerID2) blocked.
	 */
	public void removeBlocked(int playerID, int playerID2) {

	}

	/**
	 * Adds a Game to a Player's Game list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param gameID
	 *            The Game's ID.
	 * @require Player does not already have the Game.
	 */
	public void addGame(int playerID, String gameID) {

	}

	/**
	 * Removes a Game from a Player's Game list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param gameID
	 *            The Game's ID.
	 * @require Player already has the Game.
	 */
	public void removeGame(int playerID, String gameID) {

	}

}
