package deco2800.server.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.protocol.game.GameStatusUpdateResponse;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.game.NewGameResponse;
import deco2800.server.GameServer;
import deco2800.server.blackjack.BlackjackServer;

public class GameListener extends Listener {
	
	private Map<String, Map<String, Set<Connection>>> gameSessions;
	private Map<String, GameServer> gameServers;  // Dane's addition to host the game servers.
	
	/**
	 * Instantiates a HashMap of game sessions
	 */
	public GameListener(){
		this.gameSessions = new HashMap<String, Map<String, Set<Connection>>>();
		this.gameServers = new HashMap<String, GameServer>();
	}
	
	@Override
	/**
	 * received takes a connection input and an object input and stores the data
	 * in a userConnections map. 
	 * @require inputs are connection and object 
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof NewGameRequest){
			
			NewGameRequest newGameRequest = (NewGameRequest) object;
			String username = newGameRequest.username;
			String gameId = newGameRequest.gameId;
			GameRequestType requestType = newGameRequest.requestType;
			
			switch (requestType){
			case NEW:
				handleNewGameRequest(connection, username, gameId);
			case JOIN:
				Map<String, Set<Connection>> userConnections;
				if (gameSessions.containsKey(gameId)){
					//Sessions for the game ID already
					userConnections = gameSessions.get(gameId);
				} else {
					//Currently no sessions
					userConnections = new HashMap<String, Set<Connection>>();
				}
				
				if (userConnections.containsKey(username)){
					Set<Connection> connections = userConnections.get(username);
					connections.add(connection);
					connection.sendTCP(NewGameResponse.OK);
				} else {
					//No session under the given username available
					connection.sendTCP(NewGameResponse.UNAVAILABLE);
				}
			}
			
		} else if (object instanceof GameStatusUpdate){
			
			GameStatusUpdate gameStatusUpdate = (GameStatusUpdate) object;
			String gameId = gameStatusUpdate.gameId;
			String username = gameStatusUpdate.username;
			
			//Find the game session using the game ID
			if (gameSessions.containsKey(gameId)){
				Map<String, Set<Connection>> userConnections = gameSessions.get(gameId);
				//Find the user sessions using the host username
				if (userConnections.containsKey(username)){
					//Get the set of connections for the game session

					Set<Connection> connections = userConnections.get(username);
					for (Connection connect : connections){
						//Forward the update out to the connected clients
						if (connect.isConnected()){
							connect.sendTCP(gameStatusUpdate);
						}
					}
					connection.sendTCP(GameStatusUpdateResponse.OK);
				} else {
					connection.sendTCP(GameStatusUpdateResponse.UNAVAILABLE);
				}
			} else {
				connection.sendTCP(GameStatusUpdateResponse.UNAVAILABLE);
			}
		}
	}

	private void handleNewGameRequest(Connection connection, String username, String gameId) {
		Map<String, Set<Connection>> userConnections;
		if (gameSessions.containsKey(gameId)){
			//Sessions for the game ID already
			userConnections = gameSessions.get(gameId);
		} else {
			//Currently no sessions
			userConnections = new HashMap<String, Set<Connection>>();
			if (gameId.equals("blackjack")) {
				gameServers.put("blackjack", new BlackjackServer());
			}
		}
		
		//Check if there is already a session registered under the username
		Set<Connection> connections;
		if (userConnections.containsKey(username)){
			//There are already connected users - clear them out
			connections = userConnections.get(username);
			connections.clear();
		} else {
			connections = new HashSet<Connection>();
		}
		connections.add(connection);
		connection.sendTCP(NewGameResponse.OK);
	}
	
}
