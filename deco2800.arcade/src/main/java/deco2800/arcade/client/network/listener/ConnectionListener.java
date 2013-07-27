package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.protocol.connect.ConnectionResponse;

public class ConnectionListener extends NetworkListener {
	
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
		
		if (object instanceof ConnectionResponse){
			ConnectionResponse connectionResponse = (ConnectionResponse)object;
			switch(connectionResponse){
			case OK:
				Arcade arcade = Arcade.getInstance(); //Get the arcade instance
				//arcade.requestGameSession(new TicTacToe()); //TODO start up with the actual game
				arcade.selectGame();
			case REFUSED:
				//TODO error message
			case ERROR:
				//TODO error message
			}
		}
	}

	
}
