package deco2800.server.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;

public class MultiplayerListener extends Listener {
	//Map holding GameId, username, userconnection, and connection destination
	private Map<String, Map<String, Connection[]>> queueSession;
	
	public MultiplayerListener() {
		this.queueSession = new HashMap<String, Map<String, Connection[]>>();
	}
	
	@Override
	/**
	 * received takes a connection input and an object input and stores the data
	 * in a queueSessions map. 
	 * @require inputs are connection and object 
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		//Fill out map
		if (object instanceof NewMultiGameRequest) {
			NewMultiGameRequest multiRequest = (NewMultiGameRequest) object;
			String username = multiRequest.username;
			String gameId = multiRequest.gameId;
			GameRequestType requestType = multiRequest.requestType;
			Connection connectTo = multiRequest.connectTo;
			
			switch (requestType){
			case NEW:
				handleNewMultiRequest(connection, username, gameId, connectTo);
			}	
			
		}
	}
	
	/*For 2P Games with no MMR*/
	private void handleNewMultiRequest(Connection connection, String username, String gameId,
			Connection connectTo) {
		if (queueSession.get(gameId) == null) {
			Connection[] gameConnections = new Connection[2];
			gameConnections[0] = connection;
			gameConnections[1] = connectTo;
			Map<String, Connection[]> userConnection = new HashMap<String, Connection[]>();
			userConnection.put(username, gameConnections);
			queueSession.put(gameId, userConnection);
			return;
		} else {
			connection.sendTCP(connectTo);
			connection.sendTCP(NewMultiResponse.OK);
			connectTo.sendTCP(connection);
			connectTo.sendTCP(NewMultiResponse.OK);
		}
		
		return;
	}
	
	
	
}
