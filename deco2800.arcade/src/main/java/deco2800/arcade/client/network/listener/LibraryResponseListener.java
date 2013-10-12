package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.game.GameLibraryResponse;


/**
 * Class to listen to game library responses from server
 * @author Aaron Hayes
 */
public class LibraryResponseListener extends NetworkListener {

    public LibraryResponseListener() {
    }

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

    /**
     * Update the list of games on the arcade system
     *  if a LibraryResponse is received
     * @param connection Connection
     * @param object Network Object
     */
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

        if (object instanceof GameLibraryResponse) {
            GameLibraryResponse gameLibraryResponse = (GameLibraryResponse) object;
            ArcadeSystem.updateGamesList(gameLibraryResponse.games);
        }
	}

	
}
