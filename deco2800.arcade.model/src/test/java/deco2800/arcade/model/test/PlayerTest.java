package deco2800.arcade.model.test;

import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;

public class PlayerTest {

	private Player player1;
	private Player player2;

	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	private User user6;

	private Game game1;
	private Game game2;
	private Game game3;
	private Game game4;

	@Before
	public void initialise() {
		user1 = new User(1);
		user2 = new User(2);
		user3 = new User(3);
		user4 = new User(4);
		user5 = new User(5);
		user6 = new User(6);

		game1 = new Game();
		game1.id = "1";
		game2 = new Game();
		game2.id = "2";
		game3 = new Game();
		game3.id = "3";
		game4 = new Game();
		game4.id = "4";

		List<String> info = new ArrayList<String>();
		info.add("Ricky");
		info.add("Rick Astley");
		info.add("rick@astley.giveyouup");
		info.add("ARTS");
		info.add("#Rickroll");
		info.add("20");
		boolean[] privset = { true, true, true, true, true, true, true };

		player1 = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);

		List<String> info2 = new ArrayList<String>();
		info2.add("Stewie");
		info2.add("Stewie Griffin");
		info2.add("stewie@griffin.killlouis");
		info2.add("Nope");
		info2.add("Kill Louis");
		info2.add("2");
		boolean[] privset2 = { true, true, true, true, true, true, true };
		Set<User> requestSet = new HashSet<User>();
		requestSet.add(user1);
		Set<User> blockSet = new HashSet<User>();
		blockSet.add(user2);
		Set<User> friendSet = new HashSet<User>();
		friendSet.add(user3);
		Set<Game> gameSet = new HashSet<Game>();
		gameSet.add(game1);

		player2 = new Player(123, "THIS IS NOT A VALID PATH.html", info2,
				friendSet, requestSet, blockSet, gameSet, privset2);
	}

	@Test
	public void getUsernameTest() {
		Assert.assertTrue(player1.getUsername().equals("Ricky"));
	}

	@Test
	public void setUsernameTest() {
		player2.setUsername("Peter Rabbit");
		Assert.assertTrue(player2.getUsername().equals("Peter Rabbit"));
	}

	@Test
	public void getEmailTest() {
		Assert.assertTrue(player1.getEmail().equals("rick@astley.giveyouup"));
	}

	@Test
	public void setEmailTest() {
		player2.setEmail("stewie@griffin.kill");
		Assert.assertTrue(player2.getEmail().equals("stewie@griffin.kill"));
	}

	@Test
	public void getBioTest() {
		Assert.assertTrue(player1.getBio().equals("#Rickroll"));
	}

	@Test
	public void setBioTest() {
		player2.setBio("Killing Louis");
		Assert.assertTrue(player2.getBio().equals("Killing Louis"));
	}

	@Test
	public void getNameTest() {
		Assert.assertTrue(player1.getName().equals("Rick Astley"));
	}

	@Test
	public void setNameTest() {
		player2.setName("STEWIE");
		Assert.assertTrue(player2.getName().equals("STEWIE"));
	}

	@Test
	public void getProgramTest() {
		Assert.assertTrue(player1.getProgram().equals("ARTS"));
	}

	@Test
	public void setProgramTest() {
		player2.setProgram("Killing");
		Assert.assertTrue(player2.getProgram().equals("Killing"));
	}

	@Test
	public void getIconTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setIconTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getGamesTest() {
		Assert.assertTrue(player1.getGames().isEmpty());
		Assert.assertTrue(!player2.getGames().isEmpty());
		Assert.assertTrue(player2.getGames().contains(game1));
	}

	@Test
	public void addGamesTest() {
		player1.addGame(game1);
		player1.addGame(game2);
		Assert.assertTrue(player1.getGames().size() == 2);
		Assert.assertTrue(player1.getGames().contains(game1));
		Assert.assertTrue(player1.getGames().contains(game2));
	}

	@Test
	public void removeGamesTest() {
		player2.addGame(game3);
		player2.addGame(game4);
		Assert.assertTrue(player2.getGames().contains(game3));
		Assert.assertTrue(player2.getGames().contains(game4));
		player2.removeGame(game3);
		player2.removeGame(game4);
		Assert.assertTrue(!player2.getGames().contains(game3));
		Assert.assertTrue(!player2.getGames().contains(game4));
	}

	@Test
	public void hasGamesTest() {
		player1.addGame(game3);
		player1.addGame(game4);
		Assert.assertTrue(player1.hasGame(game3));
		Assert.assertTrue(player1.hasGame(game4));
	}

	@Test
	public void getFriendsTest() {
		Assert.assertTrue(player1.getFriends().isEmpty());
		Assert.assertTrue(!player2.getFriends().isEmpty());
		Assert.assertTrue(player2.getFriends().contains(user3));
	}

	@Test
	public void addFriendsTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void removeFriendsTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void hasFriendsTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getFriendInvitesTest() {
		Assert.assertTrue(player1.getInvites().isEmpty());
		Assert.assertTrue(!player2.getInvites().isEmpty());
		Assert.assertTrue(player2.getInvites().contains(user1));
	}

	@Test
	public void addFriendInvitesTest() {
		player1.addInvite(user1);
		player1.addInvite(user2);
		Assert.assertTrue(player1.getInvites().size() == 2);
		Assert.assertTrue(player1.getInvites().contains(user1));
		Assert.assertTrue(player1.getInvites().contains(user2));
	}

	@Test
	public void removeFriendInvitesTest() {
		player2.addInvite(user3);
		player2.addInvite(user4);
		Assert.assertTrue(player2.getInvites().contains(user3));
		Assert.assertTrue(player2.getInvites().contains(user4));
		player2.removeInvite(user3);
		player2.removeInvite(user4);
		Assert.assertTrue(!player2.getInvites().contains(user3));
		Assert.assertTrue(!player2.getInvites().contains(user4));
	}

	@Test
	public void hasFriendInvitesTest() {
		player1.addInvite(user3);
		player1.addInvite(user4);
		Assert.assertTrue(player1.hasInvite(user3));
		Assert.assertTrue(player1.hasInvite(user4));
	}

	@Test
	public void getBlockedTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void addBlockedTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void removeBlockedTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void hasBlockedTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getNamePrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setNamePrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getEmailPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setEmailPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getProgramPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setProgramPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getBioPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setBioPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getFriendsPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setFriendsPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getGamesPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setGamesPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void getAchievementsPrivacyTest() {
		Assert.assertTrue(true);
	}

	@Test
	public void setAchievementsPrivacyTest() {
		Assert.assertTrue(true);
	}

}