package deco2800.server.listener;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.player.*;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerDatabaseManager;
import deco2800.arcade.model.*;

public class PlayerListener extends Listener{
	
	
	//uses DatabaseManager to talk to database
	public PlayerListener() {
		
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof BioUpdateRequest) {
			BioUpdateRequest request = (BioUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof BlockedUpdateRequest) {
			BlockedUpdateRequest request  = (BlockedUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof EmailUpdateRequest) {
			EmailUpdateRequest request = (EmailUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof FriendInvitesUpdateRequest) {
			FriendInvitesUpdateRequest request = (FriendInvitesUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof FriendsUpdateRequest) {
			FriendsUpdateRequest request = (FriendsUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof GamesUpdateRequest) {
			GamesUpdateRequest request = (GamesUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof NameUpdateRequest) {
			NameUpdateRequest request = (NameUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof ProgramUpdateRequest) {
			ProgramUpdateRequest request = (ProgramUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
			
		} else if (object instanceof UsernameUpdateRequest) {
			UsernameUpdateRequest request = (UsernameUpdateRequest) object;
			PlayerDatabaseManager pdm = new PlayerDatabaseManager();
		} else if (object instanceof PlayerRequest) {
			PlayerRequest request = (PlayerRequest)object;
            PlayerResponse response = new PlayerResponse();
            int playerID =  request.playerID;
            PlayerDatabaseManager pdm = new PlayerDatabaseManager();
            
            try {
				//update database
            	response.player = pdm.loadPlayer(playerID);
                BlockingMessage.respond(connection, request, response);
			} catch (DatabaseException e) {
				e.printStackTrace();
                // can't let the client keep blocking, just send an empty list
				response.player = null;
                BlockingMessage.respond(connection, request, response);
            }
		} else if (object instanceof LibraryStyleUpdateRequest) {
            LibraryStyleUpdateRequest request = (LibraryStyleUpdateRequest) object;
            PlayerDatabaseManager pdm = new PlayerDatabaseManager();
        }
	}
}
