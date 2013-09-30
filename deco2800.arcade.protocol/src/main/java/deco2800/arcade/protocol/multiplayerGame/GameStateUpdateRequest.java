package deco2800.arcade.protocol.multiplayerGame;

import deco2800.arcade.protocol.game.GameRequest;

public class GameStateUpdateRequest extends GameRequest {
	public String username;
	public int gameSession;
	public Object stateChange;
	public Boolean gameOver;

}
