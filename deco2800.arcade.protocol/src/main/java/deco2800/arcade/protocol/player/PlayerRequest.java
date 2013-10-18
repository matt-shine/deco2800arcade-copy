package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.BlockingMessage;

public class PlayerRequest extends BlockingMessage {
	public int playerID;
	
	/**
	 * Zero-arg Constructor for Kryo
	 */
	public PlayerRequest(){
	}
}
