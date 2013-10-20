package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.FriendInvites;
import deco2800.arcade.model.Friends;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;
import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class FriendTest {

	private Player player;
	private Player player2;
	private Player player3;
	private Player player4;
	
	@Before
	public void initialise() {
		
		List<String> info = new ArrayList<String>();
		info.add("Foo");
		info.add("Foo Bar");
		info.add("foo@bar.com");
		info.add("IT");
		info.add("#Rickroll");
		info.add("20");
		
		ArrayList<Boolean> privset = new ArrayList<Boolean>();
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);

		player = new Player(123, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		
		player2 = new Player(222, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		
		player3 = new Player(333, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);
		
		player4 = new Player(444, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);

	}
	
	@Test
	/**
	 *  Test player are created with ID's and username
	 */
	public void initialTest(){
		
		Assert.assertTrue(player.getUsername().equals("Foo"));
		Assert.assertTrue(player.getID() == 123);
		
		Assert.assertTrue(player2.getUsername().equals("Foo"));
		Assert.assertTrue(player2.getID() == 222);
		
		Assert.assertTrue(player3.getUsername().equals("Foo"));
		Assert.assertTrue(player3.getID() == 333);
		
		Assert.assertTrue(player4.getUsername().equals("Foo"));
		Assert.assertTrue(player4.getID() == 444);
		
	}
	
	@Test
	/**
	 *  Check can add to invite list
	 */
	public void sendInvite() {

		player.addInvite(player2);
		player.addInvite(player3);
		player.addInvite(player4);
		Assert.assertTrue(player.hasInvite(player2));
		Assert.assertTrue(player.hasInvite(player3));
		Assert.assertTrue(player.hasInvite(player4));

	}
	
	@Test
	/**
	 *  Check Friends list starts empty
	 */
	public void checkfriends() {

		Assert.assertTrue(player.getFriends().isEmpty());

	}
	
	@Test
	/**
	 *  Player can accept an invite
	 */
	public void acceptInvite() {
		
		player.addInvite(player2);
		player.addInvite(player3);
		player.addInvite(player4);

		player.acceptFriendInvite(player2);
		player.acceptFriendInvite(player3);
		player.acceptFriendInvite(player4);
		
		Assert.assertTrue(player.getFriends().contains(player2));
		Assert.assertTrue(player.getFriends().contains(player3));
		Assert.assertTrue(player.getFriends().contains(player4));
	
	}
	
	@Test
	/**
	 *  Player can remove an invite
	 */
	public void removeinvite() {
		
		player.addInvite(player2);
		Assert.assertTrue(player.hasInvite(player2));
		player.removeInvite(player2);
		Assert.assertFalse(player.hasInvite(player2));
		
	}
	
	
	@Test
	/**
	 *  Check player can add friends
	 */
	public void addfriends() {

		player2.addInvite(player);
		player2.acceptFriendInvite(player);
		Assert.assertTrue(player2.getFriends().contains(player));

	}
	
	@Test
	/**
	 *  Check player can remove friends
	 */
	public void removefriend() {
		
		//Add Friends
		player.addInvite(player2);
		player.addInvite(player3);
		player.addInvite(player4);

		player.acceptFriendInvite(player2);
		player.acceptFriendInvite(player3);
		player.acceptFriendInvite(player4);

		Assert.assertTrue(player.getFriends().contains(player2));
		Assert.assertTrue(player.getFriends().contains(player3));
		Assert.assertTrue(player.getFriends().contains(player4));
		
		//Remove Friends
		player.removeFriend(player2);
		player.removeFriend(player3);
		
		Assert.assertFalse(player.getFriends().contains(player2));
		Assert.assertFalse(player.getFriends().contains(player3));
		Assert.assertTrue(player.getFriends().contains(player4));
				
	}
	
	@Test
	/**
	 *  Player gets added to blocked list
	 */
	public void blockfriend() {
		
		player.blockPlayer(player2);
		player2.getFriends().add(player);
		Assert.assertFalse(player2.getFriends().contains(player));
		
	}
	
	@Test
	/**
	 *  Player gets removed from blocked list
	 */
	public void unblockfriend() {
		
		player.blockPlayer(player2);
		player2.getFriends().add(player);
		Assert.assertFalse(player2.getFriends().contains(player));
		player.removeBlocked(player2);
		Assert.assertFalse(player.getBlockedList().contains(player2));
		
	}

}
