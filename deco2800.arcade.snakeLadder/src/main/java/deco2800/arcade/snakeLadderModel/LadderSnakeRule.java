package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.*;
import deco2800.arcade.snakeLadderModel.GamePlayer;
public class LadderSnakeRule implements Rule {

	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		// TODO Auto-generated method stub
		int destinationPosition = Integer.parseInt(rule.substring(1))-1;
		//get the current tile
		Tile currentTile = context.getMap().getTileList()[context.gamePlayers[playerNum].newposition()];
		//set player's position to destination position
		context.gamePlayers[playerNum].setNewPosition(destinationPosition);
		//get the destination tile
	    Tile destinationTile = context.getMap().getTileList()[context.gamePlayers[playerNum].newposition()];
	    Vector2 velocity = new Vector2(destinationTile.getCoorX()-currentTile.getCoorX(),destinationTile.getCoorY()-currentTile.getCoorY());
	    //set this velocity to player's velocity
	    context.gamePlayers[playerNum].setVelocity(velocity);
	    context.gameState = new MovingState();
	}

}
