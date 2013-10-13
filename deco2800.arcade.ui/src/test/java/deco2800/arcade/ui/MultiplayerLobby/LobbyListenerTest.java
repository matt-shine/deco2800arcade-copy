package deco2800.arcade.ui.MultiplayerLobby;

import org.junit.Before;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;

public class LobbyListenerTest {

	@Before
	public void Setup() {
		CreateMatchRequest cmr1 = new CreateMatchRequest();
		cmr1.gameId = "1";
		cmr1.hostConnection = null;
	}

}
