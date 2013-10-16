 package deco2800.server.database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;

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

	public PlayerDatabaseManager() {
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
	 *          the Player class. Player with playerID exists.
	 */
	public void updateUsername(int playerID, String username) {
		try {
			playerStorage.updateUsername(playerID, username);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Updates the players bio to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param bio
	 *            The Player's new bio.
	 * @require Bio is valid, that is it obeys all restrictions imposed by the
	 *          Player class. Player with playerID exists.
	 */
	public void updateBio(int playerID, String bio) {
		try {
			playerStorage.updateBio(playerID, bio);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Updates the players email to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param email
	 *            The Player's new email.
	 * @require Email is valid, that is it obeys all restrictions imposed by the
	 *          Player class. Player with playerID exists.
	 */
	public void updateEmail(int playerID, String email) {
		try {
			playerStorage.updateEmail(playerID, email);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Updates the players program to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param program
	 *            The Player's new program.
	 * @require Program is valid, that is it obeys all restrictions imposed by
	 *          the Player class. Player with playerID exists.
	 */
	public void updateProgram(int playerID, String program) {
		try {
			playerStorage.updateProgram(playerID, program);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Updates the players name to that provided.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param name
	 *            The Player's new name.
	 * @require Name is valid, that is it obeys all restrictions imposed by the
	 *          Player class. Player with playerID exists.
	 */
	public void updateName(int playerID, String name) {
		try {
			playerStorage.updateName(playerID, name);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Adds a friend to a Player's friends list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param friendID
	 *            The friend's playerID.
	 * @require Player and Friend are not already friends. Players with playerID
	 *          and friendID exist.
	 */
	public void addFriend(int playerID, int friendID) {
		try {
			// TODO verify that this is the correct method
			friendStorage.acceptFriendRequest(playerID, friendID);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Removes a friend from a Player's friends list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param friendID
	 *            The friend's playerID.
	 * @require Player and Friend are friends. Players with playerID and
	 *          friendID exist.
	 */
	public void removeFriend(int playerID, int friendID) {
		try {
			// TODO verify that this is the correct method
			friendStorage.removeFriend(playerID, friendID);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Adds a Player (playerID2) to a Player's (playerID1) blocked list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param playerID2
	 *            The blocked Player's playerID.
	 * @require Player does not already have Player (playerID2) blocked. Players
	 *          with playerID and playerID2 exist.
	 */
	public void addBlocked(int playerID, int playerID2) {
		try {
			// TODO verify that this is the correct method
			friendStorage.blockPlayer(playerID, playerID2);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Removes a Player (playerID2) from a Player's (playerID1) blocked list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param playerID2
	 *            The blocked Player's playerID.
	 * @require Player already has Player (playerID2) blocked. Players with
	 *          playerID and playerID2 exist.
	 */
	public void removeBlocked(int playerID, int playerID2) {
		try {
			// TODO verify that this is the correct method
			friendStorage.blockPlayer(playerID, playerID2);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Adds a Game to a Player's Game list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param gameID
	 *            The Game's ID.
	 * @require Player does not already have the Game. Players with playerID
	 *          exists, and Game with gameID exists.
	 */
	public void addGame(int playerID, String gameID) {
		try {
			playerGameStorage.addPlayerGames(playerID, gameID);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Removes a Game from a Player's Game list.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param gameID
	 *            The Game's ID.
	 * @require Player already has the Game. Players with playerID exists, and
	 *          Game with gameID exists.
	 */
	public void removeGame(int playerID, String gameID) {
		try {
			playerGameStorage.removeGame(playerID, gameID);
		} catch (DatabaseException e) {
			// TODO Error catch?
		}
	}

	/**
	 * Updates a Player's Bio field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateBioPrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateBio(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Updates a Player's Name field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateNamePrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateName(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Updates a Player's Email field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateEmailPrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateEmail(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Updates a Player's Program field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateProgramPrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateProgram(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Updates a Player's Games field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateGamesPrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateGames(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Updates a Player's Achievements field privacy setting.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @param privacySetting
	 *            True if field is to be publicly visible, false for friends
	 *            access only
	 */
	public void updateAchievementsPrivacy(int playerID, boolean privacySetting) {
		try {
			playerPrivacy.updateAchievements(playerID, privacySetting);
		} catch (DatabaseException e) {
			// TODO Error catch
		}
	}

	/**
	 * Loads a player from the database.
	 * 
	 * @param playerID
	 *            The Player's playerID.
	 * @return Returns the player with the provided playerID, given that it
	 *         exists.
	 */
	public Player loadPlayer(int playerID) throws DatabaseException {
		List<String> playerData = playerStorage.getPlayerData(playerID);
		List<Integer> friends = friendStorage.getFriendsList(playerID);
		List<Integer> friendInvites = friendStorage
				.getFriendInviteList(playerID);
		List<Integer> blocked = friendStorage.getBlockedList(playerID);
		List<Integer> privacyData = playerPrivacy.getPlayerData(playerID);
		Set<String> gameData = playerGameStorage.getPlayerGames(playerID);

		Set<User> friendsSet = new HashSet<User>();
		Set<User> invitesSet = new HashSet<User>();
		Set<User> blockedSet = new HashSet<User>();
		Set<Game> gameSet = new HashSet<Game>();
		boolean[] privacy = {false, false, false, false, false, false, false};
		privacy[0] = false;

		for (int i : friends) {
			friendsSet.add(new User(i));
		}

		for (int i : friendInvites) {
			invitesSet.add(new User(i));
		}

		for (int i : blocked) {
			blockedSet.add(new User(i));
		}

		//for (String i : gameData) {
			//gameSet.add(new Game(i));
		//}

		for (int i = 0; i < privacyData.size(); i++) {
			if (privacyData.get(i) == 1) {
				privacy[i] = true;
			} else {
				privacy[i] = false;
			}
		}

		/**
		 * What's this?! I hear you ask.
		 * 
		 * Well, this check shouldn't be necessary when we have playerID's under control, but for the moment
		 * the client is sending any old playerID over here and the database most likely won't find a player
		 * with that ID. Hence this check.
		 * 
		 * When playerID's are ready to go, this method "should" only be called with legitimate ID's and this
		 * can be removed.
		 * 
		 */
		if (!playerData.isEmpty()){
			Player player = new Player(playerID, null, playerData, friendsSet,
					invitesSet, blockedSet, gameSet, privacy);
			return player;
		} else{
			return null;
		}
		
	}

}
