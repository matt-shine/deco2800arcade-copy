package multiplayerGame;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.GameRequest;
import deco2800.arcade.protocol.game.GameRequestType;

public class NewMatchmakingRequest extends GameRequest {

	public GameRequestType requestType;

}
