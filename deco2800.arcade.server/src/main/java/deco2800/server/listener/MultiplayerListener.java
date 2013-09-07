package deco2800.server.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.server.MultiplayerServer;

public class MultiplayerListener extends Listener {
	//Map holding GameId, username, userconnection, and connection destination
	private Map<String, Map<String, Connection>> queueSession;
	private ArrayList<MultiplayerServer> activeServers;
	
	public MultiplayerListener() {
		this.queueSession = new HashMap<String, Map<String, Connection>>();
		this.activeServers = new ArrayList<MultiplayerServer>();
	}
	
	@Override
	/**
	 * received takes a connection input and an object input and stores the data
	 * in a queueSessions map. 
	 * @require inputs are connection and object 
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof NewMultiGameRequest) {
			System.out.println("Received connection");
			NewMultiGameRequest multiRequest = (NewMultiGameRequest) object;
			String username = multiRequest.username;
			String gameId = multiRequest.gameId;
			MultiGameRequestType requestType = multiRequest.requestType;
			//Connection connectTo = multiRequest.connectTo;
			
			switch (multiRequest.requestType){
			case NEW:
				System.out.println("Connection Received");
				connection.sendTCP(NewMultiResponse.OK);
				handleNewMultiRequest(connection, username, gameId);
				break;
			case JOIN:
				//TODO: 2+ player games
				handleJoinMultiRequest();
				break;
			default:
				break;
			}	
		} else if (object instanceof GameStateUpdateRequest) {
			//Sends update to server to broadcast
			GameStateUpdateRequest request = (GameStateUpdateRequest) object;
			MultiplayerServer server = activeServers.get(request.gameSession);
			server.stateUpdate(request.username, request.stateChange);
		}
	}
	
	
	/*For 2P Games with no MMR*/
	private void handleNewMultiRequest(Connection connection, String username, String gameId) {
		System.out.println("gameID: " + gameId);
		System.out.println("size: " + queueSession.size());
		if (queueSession.get(gameId) == null) {
			Map<String, Connection> userConnection = new HashMap<String, Connection>();
			userConnection.put(username, connection);
			queueSession.put(gameId, userConnection);
			return;
		} else {
			Map<String, Connection> player2 = queueSession.get(gameId);
			String player2Name = (String) player2.keySet().toArray()[0];
			Connection player2Connection = player2.get(player2Name);
			if (connection.equals(player2Connection)) {
				System.out.println("Connections identical");
			}
			connection.sendTCP(new NewMultiGameRequest()); //testing
			connection.sendTCP("Game Found");
			player2Connection.sendTCP(new NewMultiGameRequest()); //testing
			player2Connection.sendTCP("Game Found");
			MultiplayerServer gameServer =  new MultiplayerServer(username, player2Name, connection, 
					player2Connection, gameId, activeServers.size());
			activeServers.add(gameServer);
			queueSession.remove(gameId);
		}
		
		return;
	}
	
	private void handleJoinMultiRequest() {
		//TODO: this method. 
	}
	
	
	
}
