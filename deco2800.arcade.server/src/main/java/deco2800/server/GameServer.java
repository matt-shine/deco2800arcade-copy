package deco2800.server;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.CasinoServerUpdate;
import deco2800.arcade.protocol.game.GameStatusUpdate;

public interface GameServer {
	
	void receive(Connection connection, CasinoServerUpdate object); 
}
