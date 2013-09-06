package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.game.NewGameResponse;

public class GameListener extends NetworkListener {

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof NewGameResponse){
			
			@SuppressWarnings("unused")
			NewGameResponse newGameResponse = (NewGameResponse) object;
			
			//TODO something
		}
	}

	
}
