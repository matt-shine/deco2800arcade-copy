package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.protocol.game.GameLibraryRequest;
import deco2800.arcade.protocol.game.GameLibraryResponse;

import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

/**
 * Class to listen for requests for the game library
 * @author Aaron Hayes
 */
public class LibraryListener extends Listener {

    /**
     * Basic Constructor
     */
	public LibraryListener() {
		try {
			ArcadeServer.instance().getGameStorageDatabase().initialise();
		} catch(DatabaseException e ) {
            e.printStackTrace();
		}
	}

    /**
     * When a GameLibraryRequest Object is received send a response
     * @param connection Connection
     * @param object Network Request
     */
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (object instanceof GameLibraryRequest) {
            GameLibraryResponse response = new GameLibraryResponse();
            try {
                response.games = ArcadeServer.instance().getGameStorageDatabase().getServerGames();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            connection.sendTCP(response);
        }
    }
}
