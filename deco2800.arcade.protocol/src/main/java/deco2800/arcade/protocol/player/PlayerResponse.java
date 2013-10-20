package deco2800.arcade.protocol.player;

import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.BlockingMessage;

public class PlayerResponse extends BlockingMessage {
	private Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
