package deco2800.arcade.protocol.achievement;

import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.model.Achievement;
import java.util.ArrayList;

public class AchievementsForIDsResponse extends BlockingMessage {

	public ArrayList<Achievement> achievements;
	
}
