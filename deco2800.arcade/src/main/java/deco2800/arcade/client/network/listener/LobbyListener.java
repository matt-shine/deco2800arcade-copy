package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.RemovedMatchDetails;

public class LobbyListener extends NetworkListener {
	
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
		
		if (object instanceof ActiveMatchDetails) {
			Arcade.addToMatchList((ActiveMatchDetails) object);
		}
		
		else if (object instanceof RemovedMatchDetails) {
			Arcade.removeFromMatchList((RemovedMatchDetails)object);
		}
		else if (object instanceof CreateMatchRequest) {
			ArcadeSystem.createMatch((CreateMatchRequest) object);
		}
	}
}
