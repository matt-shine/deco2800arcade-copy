package deco2800.arcade.protocol.player;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;

public class PlayerResponse extends BlockingMessage {
	public Player player;
	
	/**
	 * Zero-arg Constructor for Kryo
	 */
	public PlayerResponse(){
	}
}
