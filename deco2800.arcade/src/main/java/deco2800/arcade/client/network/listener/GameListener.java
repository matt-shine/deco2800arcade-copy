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
			NewGameResponse newGameResponse = (NewGameResponse) object;
			
			switch (newGameResponse) {
			case OK:

			case REFUSED:

			case ERROR:
				//TODO handle error
			case UNAVAILABLE:
				//TODO handle UNAVAILABLE
			}
		}
	}

	
}
