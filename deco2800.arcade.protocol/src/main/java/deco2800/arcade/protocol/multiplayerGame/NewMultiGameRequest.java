package deco2800.arcade.protocol.multiplayerGame;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.GameRequest;
import deco2800.arcade.protocol.game.GameRequestType;

public class NewMultiGameRequest extends GameRequest {

	public GameRequestType requestType;
	public Connection connectTo;
	
}