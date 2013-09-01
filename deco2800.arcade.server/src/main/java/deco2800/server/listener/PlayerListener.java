package deco2800.server.listener;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.protocol.player.*;

public class PlayerListener extends Listener{
	
	
	//uses DatabaseManager to talk to database
	public PlayerListener() {
		
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof BioUpdateRequest) {
			BioUpdateRequest bioUpdateRequest = (BioUpdateRequest) object;
			
		} else if (object instanceof BlockedUpdateRequest) {
			BlockedUpdateRequest blockedUpdateRequest  = (BlockedUpdateRequest) object;
			
		} else if (object instanceof EmailUpdateRequest) {
			EmailUpdateRequest emailUpdateRequest = (EmailUpdateRequest) object;
			
		} else if (object instanceof FriendInvitesUpdateRequest) {
			FriendInvitesUpdateRequest friendInviteUpdateRequest = (FriendInvitesUpdateRequest) object;
			
		} else if (object instanceof FriendsUpdateRequest) {
			FriendsUpdateRequest friendsUpdateRequest = (FriendsUpdateRequest) object;
			
		} else if (object instanceof GamesUpdateRequest) {
			GamesUpdateRequest gamesUpdateRequest = (GamesUpdateRequest) object;
			
		} else if (object instanceof NameUpdateRequest) {
			NameUpdateRequest nameUpdateRequest = (NameUpdateRequest) object;
			
		} else if (object instanceof ProgramUpdateRequest) {
			ProgramUpdateRequest programUpdateRequest = (ProgramUpdateRequest) object;
			
		} else if (object instanceof UsernameUpdateRequest) {
			UsernameUpdateRequest usernameUpdateRequest = (UsernameUpdateRequest) object;
		}
	}
}
