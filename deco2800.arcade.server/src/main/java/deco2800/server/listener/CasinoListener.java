package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.protocol.game.CasinoServerUpdate;
import deco2800.server.blackjack.BlackjackServer;

public class CasinoListener extends Listener {
	
	BlackjackServer blackjack;
	
	public CasinoListener() {
		this.blackjack = new BlackjackServer();
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof CasinoServerUpdate) {
			CasinoServerUpdate request = (CasinoServerUpdate) object;
			this.blackjack.receive(connection, request);
		}
	}
}
