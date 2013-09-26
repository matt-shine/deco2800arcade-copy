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

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

        if (object instanceof GameLibraryResponse) {
            GameLibraryResponse gameLibraryResponse = (GameLibraryResponse) object;

            /*for (Game game : gameLibraryResponse.games) {
                System.out.println("Game : " + game.name + " : " + game.description);
            }    */
            ArcadeSystem.updateGamesList(gameLibraryResponse.games);
        }
	}

	
}
