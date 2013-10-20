package deco2800.arcade.protocol.multiplayerGame;

import deco2800.arcade.protocol.game.GameRequest;

public class GameStateUpdateRequest extends GameRequest {
	public int playerID;
	public int gameSession;
	public Object stateChange;
	public boolean initial;
	public Boolean gameOver;
	public int winner;

}
