package deco2800.arcade.snakeLadderModel;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.WaitingState;
public class LadderSnakeRule implements Rule {

	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		// TODO Auto-generated method stub
		
		
		if(context.gamePlayers[playerNum].getBounds().x>=180 &&context.gamePlayers[playerNum].getBounds().y>=180)
		{
			context.gamePlayers[playerNum].getVelocity().x=60;
			context.gamePlayers[playerNum].getVelocity().y=60;
			context.gamePlayers[playerNum].move(2);
			context.gamePlayers[playerNum].reset();
			context.gamePlayers[playerNum].setNewPosition(68);
			context.gameState = new WaitingState();
		}
		if(context.gamePlayers[playerNum].getBounds().x<=(60-20f) && context.gamePlayers[playerNum].getBounds().y>=300)
		{
			context.gamePlayers[playerNum].getVelocity().x=60;
			context.gamePlayers[playerNum].getVelocity().y=-60;
			context.gamePlayers[playerNum].move(2);
			context.gamePlayers[playerNum].reset();
			context.gamePlayers[playerNum].setNewPosition(38);
			context.gameState=new WaitingState();
		}
	}

}
