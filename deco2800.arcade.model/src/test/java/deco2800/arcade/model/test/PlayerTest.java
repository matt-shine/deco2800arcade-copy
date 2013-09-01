package deco2800.arcade.model.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;

public class PlayerTest {

	@Test
	public void PlayerConstructorTest() {
		Player p1 = new Player(111, "Bob", "String filepath");
		Player p2 = new Player(112, "Betty", "String filepath");
		
	}

	@Test
	public void GetUsernameTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Assert.assertEquals("Mismatched player name", "Ezmerelda", p1.getUsername());
	}
	
	@Test
	public void getUsernameTest2() {
		//TODO Implement exception throwing in player constructor
		Player p2 = new Player(112, " ", "String filepath");
		//Assert.assertEquals("Player name error", "Invalid username", p2.getUsername());
	}
	
	@Test
	public void SetUsernameTest() {
		Player p1 = new Player(111, "Bob", "String filepath");
		Player p2 = new Player(112, null, "String filepath");
		Player p3 = new Player(113, "Genie", "String filepath");
		Player p4 = new Player(114, "Zeus", "String filepath");
		Player p5 = new Player(115, "Dekota", "String filepath");
		p1.setUsername("Cornelious");
		p2.setUsername("GameDestroya");
		p3.setUsername("%$^&%@@#$");
		p4.setUsername(" ");
		p5.setUsername("Cornelious");
		Assert.assertEquals("Mismatched player name update", "Cornelious", p1.getUsername());
		Assert.assertEquals("Player name assigned to null value", "Invalid username update", p2.getUsername());
		Assert.assertEquals("Invalid player name characters", "Invalid chars", p3.getUsername());
		Assert.assertEquals("Invalid player name characters", "Invalid chars", p4.getUsername());
		Assert.assertEquals("Invalid player name, already in use", "Non-unique Username", p5.getUsername());
	}
	
	@Test
	public void GetGamesTest() {
		//TO DO

	}
	
	@Test
	public void AddGamesTest() {
		//TO DO

	}
	
	@Test
	public void RemoveGameTest() {
		//TO DO
	}
	
	@Test
	public void GetFriendsTest() {
		//TODO fix this, it will return ids, not names.
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		Player p3 = new Player(113, "FriendlyMoe", "String filepath");
		Player p4 = new Player(114, "FriendlyBoe", "String filepath");
		Player p5 = new Player(115, "FriendlyToe", "String filepath");
		p1.addFriend(p2);
		p1.addFriend(p3);
		p1.addFriend(p4);
		p1.addFriend(p5);
		Assert.assertEquals("Incorrect Friends List", "FriendlyJoe, FriendlyMoe, FriendlyBoe, FriendlyToe", p1.getFriends());

	}
	
	@Test
	public void RemoveFriendsTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		Player p3 = new Player(113, "FriendlyMoe", "String filepath");
		Player p4 = new Player(114, "FriendlyBoe", "String filepath");
		Player p5 = new Player(115, "FriendlyToe", "String filepath");
		p1.addFriend(p2);
		p1.addFriend(p3);
		p1.addFriend(p4);
		p1.addFriend(p5);
		p1.removeFriend(p2);
		p1.removeFriend(p3);
		Assert.assertEquals("Incorrect Friends List", "FriendlyBoe, FriendlyToe", p1.getFriends());

	}
	
	@Test
	public void AddFriendsTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		Player p3 = new Player(113, "FriendlyMoe", "String filepath");
		Player p4 = new Player(114, "FriendlyBoe", "String filepath");
		p1.addFriend(p2);
		p1.addFriend(p3);
		p1.addFriend(p4);
		
		/*
		 * Creating a new set which *should* be the same as p1's friends.
		 */
		Set<User> testSet = new HashSet<User>();
		testSet.add(new User(p2));
		testSet.add(new User(p3));
		testSet.add(new User(p4));
		
		Assert.assertTrue(testSet.containsAll(p1.getFriends()));
		Assert.assertTrue(p1.getFriends().containsAll(testSet));
	}
	
	@Test
	public void AddInvitesTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		Player p3 = new Player(113, "FriendlyMoe", "String filepath");
		p1.addInvite(p2);
		p1.addInvite(p3);
		Assert.assertEquals("Incorrect Invites List", "FriendlyJoe, FriendlyMoe", p1.getInvites());

	}
		
	@Test
	public void HasInvitesTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		p1.addInvite(p2);
		Assert.assertEquals("Incorrect invites status", true, p1.hasInvite(p2));

	}
	
	@Test
	public void RemoveInvitesTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "FriendlyJoe", "String filepath");
		p1.addInvite(p2);
		p1.removeInvite(p2);
		Assert.assertEquals("Incorrect invites status", false, p1.hasInvite(p2));

	}
	
	@Test
	public void GetBlockedTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "Sam", "String filepath");
		Player p3 = new Player(113, "Todd", "String filepath");
		Player p4 = new Player(114, "Zohan", "String filepath");
		Player p5 = new Player(115, "Helena", "String filepath");
		p1.blockPlayer(p2);
		p1.blockPlayer(p3);
		p1.blockPlayer(p4);
		p1.blockPlayer(p5);
		Assert.assertEquals("Incorrect Blocked List", "Sam, Todd, Zohan, Helena", p1.getBlockedList());

	}
	
	@Test
	public void IsBlockedTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "Sam", "String filepath");
		Player p3 = new Player(123, "Nice Guy", "String filepath");
		p1.blockPlayer(p2);
		Assert.assertEquals("Incorrect Blocked status", true, p1.isBlocked(p2));
		Assert.assertEquals("Incorrect Blocked status", false, p1.isBlocked(p3));
	}
	
	@Test
	public void AddBlockedTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "Sam", "String filepath");
		Player p3 = new Player(123, "Nice Guy", "String filepath");
		p1.blockPlayer(p2);
		p1.blockPlayer(p3);
		Assert.assertEquals("Incorrect Blocked List", "Sam, Nice Guy", p1.getBlockedList());

	}
	
	@Test
	public void RemoveBlockedTest() {
		Player p1 = new Player(111, "Ezmerelda", "String filepath");
		Player p2 = new Player(112, "Sam", "String filepath");
		Player p3 = new Player(123, "Nice Guy", "String filepath");
		p1.blockPlayer(p2);
		p1.blockPlayer(p3);
		Assert.assertEquals("Incorrect Blocked List", "Nice Guy", p1.getBlockedList());
	}
	
	
	

}

