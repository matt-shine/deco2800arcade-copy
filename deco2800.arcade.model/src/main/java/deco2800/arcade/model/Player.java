package deco2800.arcade.model;

import java.util.List;
import java.util.Set;

public class Player extends User {

	public static final int USERNAME_ID = 1;
	public static final int NAME_ID = 2;
	public static final int EMAIL_ID = 3;
	public static final int PROGRAM_ID = 4;
	public static final int BIO_ID = 5;
	public static final int AGE_ID = 6;

	public static final int NAME_PRIVACY_ID = 1;
	public static final int EMAIL_PRIVACY_IDNAME_ID = 2;
	public static final int PROGRAM_PRIVACY_ID = 3;
	public static final int BIO_PRIVACY_ID = 4;
	public static final int FRIENDS_PRIVACY_ID = 5;
	public static final int GAMES_PRIVACY_ID = 6;
	public static final int ACHIEVMENTS_PRIVACY_ID = 7;

	private Field username;

	private Field name;
	private Field email;
	private Field program;
	private Field bio;
	private Field age;

	private PrivacyField namePrivacy;
	private PrivacyField emailPrivacy;
	private PrivacyField programPrivacy;
	private PrivacyField bioPrivacy;
	private PrivacyField friendsPrivacy;
	private PrivacyField gamesPrivacy;
	private PrivacyField achievementsPrivacy;

	private Games games;
	private Friends friends;
	private Blocked blocked;
	private FriendInvites friendInvites;

	private LibraryStyle libraryStyle;

	private Icon icon;

	/**
	 * Creates a new player instance with PlayerID, username DO NOT USE THIS
	 * METHOD EXCEPT FOR TESTING PURPOSES
	 */
	public Player(int playerID, String username) {
		super(playerID);
		this.username = new Field(USERNAME_ID, username);
		this.name = new Field(NAME_ID, null);
		this.email = new Field(EMAIL_ID, null);
		this.program = new Field(PROGRAM_ID, null);
		this.bio = new Field(BIO_ID, null);

		this.games = new Games();

		this.friends = new Friends();

		this.friendInvites = new FriendInvites();

		this.blocked = new Blocked();

		this.namePrivacy = new PrivacyField(NAME_PRIVACY_ID, false);
		this.emailPrivacy = new PrivacyField(EMAIL_PRIVACY_IDNAME_ID, false);
		this.programPrivacy = new PrivacyField(PROGRAM_PRIVACY_ID, false);
		this.bioPrivacy = new PrivacyField(BIO_PRIVACY_ID, false);
		this.friendsPrivacy = new PrivacyField(FRIENDS_PRIVACY_ID, false);
		this.gamesPrivacy = new PrivacyField(GAMES_PRIVACY_ID, false);
		this.achievementsPrivacy = new PrivacyField(ACHIEVMENTS_PRIVACY_ID,
				false);
		this.libraryStyle = new LibraryStyle();
	}

	@Deprecated
	/**
	 * DO NOT USE THIS
	 */
	public Player(int playerID, String username, String filepath,
			boolean[] privacy) {
		super(playerID);
		this.username = new Field(USERNAME_ID, username);
		this.games = new Games();
		this.friends = new Friends();
		this.friendInvites = new FriendInvites();
		this.blocked = new Blocked();
		this.namePrivacy = new PrivacyField(NAME_PRIVACY_ID, privacy[0]);
		this.emailPrivacy = new PrivacyField(EMAIL_PRIVACY_IDNAME_ID,
				privacy[1]);
		this.programPrivacy = new PrivacyField(PROGRAM_PRIVACY_ID, privacy[2]);
		this.bioPrivacy = new PrivacyField(BIO_PRIVACY_ID, privacy[3]);
		this.friendsPrivacy = new PrivacyField(FRIENDS_PRIVACY_ID, privacy[4]);
		this.gamesPrivacy = new PrivacyField(GAMES_PRIVACY_ID, privacy[5]);
		this.achievementsPrivacy = new PrivacyField(ACHIEVMENTS_PRIVACY_ID,
				privacy[6]);
		this.libraryStyle = new LibraryStyle();

		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */

		// TODO: UTILISE REVIDES ICON API TO AVOID EXCEPTIONS - DEFAULT TO
		// PLACEHOLDER
		// this.icon = new Icon(filepath);
		/*
		 * @throws IOException Throws exception when the image cannot be found
		 * at the designated filepath.
		 */
		this.icon = null;
	}

	/**
	 * Creates a new Player given a name, achievement set and icon filename.
	 * 
	 * @param playerID
	 *            The Player's nameID
	 * @param filepath
	 *            The Player's icon filepath
	 * @param details
	 *            An array of strings containing the player's username, name,
	 *            email, program, bio and age.
	 * @param privacy
	 *            A boolean array of privacy settings.
	 * @require There are at least 7 elements in privacy array. Elements 1
	 *          through 7 (indexes 0 through 6) represent name, email, program,
	 *          bio, friends, games and achievements' privacy settings
	 *          respectively.
	 * 
	 *          There are 5 elements in the details array. Elements 1 through 5
	 *          (indexes 0 to 4) represent username, name, email, program and
	 *          bio of the player.
	 */
	public Player(int playerID, String filepath, List<String> details,
			Set<User> friendsList, Set<User> friendRequestsList,
			Set<User> blockedList, Set<Game> gamesList, boolean[] privacy) {
		super(playerID);
		this.username = new Field(USERNAME_ID, details.get(0));
		this.name = new Field(NAME_ID, details.get(1));
		this.email = new Field(EMAIL_ID, details.get(2));
		this.program = new Field(PROGRAM_ID, details.get(3));
		this.bio = new Field(BIO_ID, details.get(4));
		this.age = new Field(AGE_ID, details.get(5));

		this.games = new Games();
		if (gamesList != null) {
			this.games.addAll(gamesList);
		}

		this.friends = new Friends();
		if (friendsList != null) {
			this.friends.addAll(friendsList);
		}

		this.friendInvites = new FriendInvites();
		if (friendRequestsList != null) {
			this.friendInvites.addAll(friendRequestsList);
		}

		this.blocked = new Blocked();
		if (blockedList != null) {
			this.blocked.addAll(blockedList);
		}

		this.namePrivacy = new PrivacyField(NAME_PRIVACY_ID,
				privacy[NAME_PRIVACY_ID - 1]);
		this.emailPrivacy = new PrivacyField(EMAIL_PRIVACY_IDNAME_ID,
				privacy[EMAIL_PRIVACY_IDNAME_ID - 1]);
		this.programPrivacy = new PrivacyField(PROGRAM_PRIVACY_ID,
				privacy[PROGRAM_PRIVACY_ID - 1]);
		this.bioPrivacy = new PrivacyField(BIO_PRIVACY_ID,
				privacy[BIO_PRIVACY_ID - 1]);
		this.friendsPrivacy = new PrivacyField(FRIENDS_PRIVACY_ID,
				privacy[FRIENDS_PRIVACY_ID]);
		this.gamesPrivacy = new PrivacyField(GAMES_PRIVACY_ID,
				privacy[GAMES_PRIVACY_ID]);
		this.achievementsPrivacy = new PrivacyField(ACHIEVMENTS_PRIVACY_ID,
				privacy[ACHIEVMENTS_PRIVACY_ID - 1]);
		this.libraryStyle = new LibraryStyle();

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
	 *            string of username
	 */
	public void setUsername(String username) {
		if (username != null) {
			this.username.setValue(username);
		}
	}

	/**
	 * getEmail is an access method for the players email
	 * 
	 * @return a string of email
	 */
	public String getEmail() {
		return email.getValue();
	}

	/**
	 * Sets the email of the player
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email.setValue(email);
		// TODO
		// Do we want to add in any checking for valid email format here?
	}

	/**
	 * getBio is an access method for the players biography
	 * 
	 * @return a string of the players biography
	 */
	public String getBio() {
		return bio.getValue();
	}

	/**
	 * Sets the biography of the player
	 * 
	 * @param bio
	 */
	public void setBio(String bio) {
		this.bio.setValue(bio);
	}

	/**
	 * An access method for the players name
	 * 
	 * @return a String of the players name
	 */
	public String getName() {
		return name.getValue();
	}

	/**
	 * Sets the name of the player
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name.setValue(name);
	}

	/**
	 * An access method for the players age
	 * 
	 * @return a String of the players age
	 */
	public String getAge() {
		return age.getValue();
	}

	/**
	 * Set the players age
	 * 
	 * @param age
	 */
	public void setAge(String age) {
		this.age.setValue(age);
	}

	/**
	 * An access method for the players program
	 * 
	 * @return a String of the players program
	 */
	public String getProgram() {
		return program.getValue();
	}

	/**
	 * Sets the players program
	 * 
	 * @param program
	 */
	public void setProgram(String program) {
		this.program.setValue(program);
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
	 * @param player
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
	public void acceptFriendInvite(User friend) {
		if (friend != null  && this.hasInvite(friend)) {
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
			this.friendInvites.add(new User(player.getID()));
			setChanged();
			notifyObservers(friendInvites);
			clearChanged();
		}
	}

	/**
	 * Remove an invite from the player's invite list.
	 * 
	 * @param player
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
			this.blocked.add(new User(player.getID()));
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

	/**
	 * Set's a Player's name privacy setting
	 * 
	 * @param v
	 *            True if the Player's name is to be publicly visible, false for
	 *            friends only.
	 */
	public void setNamePrivacy(boolean v) {
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(namePrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's name privacy setting.
	 * 
	 * @return True if the Player's name is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getNamePrivacy() {
		return namePrivacy.getValue();
	}

	/**
	 * Set's a Player's email privacy setting
	 * 
	 * @param v
	 *            True if the Player's email is to be publicly visible, false
	 *            for friends only.
	 */
	public void setEmailPrivacy(boolean v) {
		emailPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(emailPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's email privacy setting.
	 * 
	 * @return True if the Player's email is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getEmailPrivacy() {
		return emailPrivacy.getValue();
	}

	/**
	 * Set's a Player's email program setting
	 * 
	 * @param v
	 *            True if the Player's program is to be publicly visible, false
	 *            for friends only.
	 */
	public void setProgramPrivacy(boolean v) {
		programPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(programPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's program privacy setting.
	 * 
	 * @return True if the Player's program is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getProgramPrivacy() {
		return programPrivacy.getValue();
	}

	/**
	 * Set's a Player's bio program setting
	 * 
	 * @param v
	 *            True if the Player's bio is to be publicly visible, false for
	 *            friends only.
	 */
	public void setBioPrivacy(boolean v) {
		bioPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(bioPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's bio privacy setting.
	 * 
	 * @return True if the Player's bio is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getBioPrivacy() {
		return bioPrivacy.getValue();
	}

	/**
	 * Set's a Player's friends privacy setting
	 * 
	 * @param v
	 *            True if the Player's friends is to be publicly visible, false
	 *            for friends only.
	 */
	public void setFriendsPrivacy(boolean v) {
		friendsPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(friendsPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's friends privacy setting.
	 * 
	 * @return True if the Player's friends is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getFriendsPrivacy() {
		return friendsPrivacy.getValue();
	}

	/**
	 * Set's a Player's games privacy setting
	 * 
	 * @param v
	 *            True if the Player's games is to be publicly visible, false
	 *            for friends only.
	 */
	public void setGamesPrivacy(boolean v) {
		gamesPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(gamesPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's games privacy setting.
	 * 
	 * @return True if the Player's games is to be publicly visible, false for
	 *         friends only.
	 */
	public boolean getGamesPrivacy() {
		return gamesPrivacy.getValue();
	}

	/**
	 * Set's a Player's achievements privacy setting
	 * 
	 * @param v
	 *            True if the Player's achievements is to be publicly visible,
	 *            false for friends only.
	 */
	public void setAchievementsPrivacy(boolean v) {
		achievementsPrivacy.setValue(v);
		namePrivacy.setValue(v);
		setChanged();
		notifyObservers(achievementsPrivacy);
		clearChanged();

	}

	/**
	 * Access method for the Player's achievements privacy setting.
	 * 
	 * @return True if the Player's achievements is to be publicly visible,
	 *         false for friends only.
	 */
	public boolean getAchievementsPrivacy() {
		return achievementsPrivacy.getValue();
	}

	/**
	 * Update Player's library style
	 * 
	 * @param style
	 *            Library Style
	 */
	public void updateLibraryLayout(int style) {
		libraryStyle.setLayout(style);
		setChanged();
		notifyObservers(libraryStyle);
		clearChanged();
	}

	/**
	 * Update Player's library colour
	 * 
	 * @param colour
	 *            Colour Scheme
	 */
	public void updateLibraryColour(int colour) {
		libraryStyle.setColourScheme(colour);
		setChanged();
		notifyObservers(libraryStyle);
		clearChanged();
	}

	/**
	 * Get Player's Library Style
	 * 
	 * @return libraryStyle
	 */
	public LibraryStyle getLibraryStyle() {
		return libraryStyle;
	}
}
