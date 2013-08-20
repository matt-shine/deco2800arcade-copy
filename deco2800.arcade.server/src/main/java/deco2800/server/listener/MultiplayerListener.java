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
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;

public class MultiplayerListener extends Listener {
	private Map<String, Map<String, Set<Connection>>> queueSession;
	
	public MultiplayerListener() {
		this.queueSession = new HashMap<String, Map<String, Set<Connection>>>();
	}
	
	@Override
	/**
	 * received takes a connection input and an object input and stores the data
	 * in a userConnections map. 
	 * @require inputs are connection and object 
	 */
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof NewMultiGameRequest) {
			NewMultiGameRequest multiRequest = (NewMultiGameRequest) object;
			String username = multiRequest.username;
			String gameId = multiRequest.gameId;
			GameRequestType requestType = multiRequest.requestType;
			
			switch (requestType){
			case NEW:
				handleNewMultiRequest(connection, username, gameId);
			}	
			
		}
	}
	
	private void handleNewMultiRequest(Connection connection, String username, String gameId) {
		return;
	}
	
	
	
}
