package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.BlockingMessage;

public class PlayerRequest extends BlockingMessage {

	private int playerID;

	/**
	 * Zero-arg Constructor for Kryo
	 */
	public PlayerRequest(){
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
}
