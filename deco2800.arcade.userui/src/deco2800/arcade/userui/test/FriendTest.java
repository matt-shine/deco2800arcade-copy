package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.AchievementScreen;
import deco2800.arcade.userui.view.StatusScreen;
import deco2800.arcade.userui.view.UserScreen;

public class FriendTest {

	private UserScreen userView;
	private StatusScreen statusView;
	private Model theModel;

	@Test
	public void inituser() {
		
		userView = new UserScreen(theModel);
		statusView = new StatusScreen(theModel);
		
	}

}
