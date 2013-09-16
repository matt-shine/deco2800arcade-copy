package deco2800.arcade.protocol.achievement;

import deco2800.arcade.protocol.BlockingMessage;
import java.util.ArrayList;

public class AchievementsForIDsRequest extends BlockingMessage {

	public ArrayList<String> achievementIDs;
	
}
