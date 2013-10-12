package deco2800.arcade.ui.MultiplayerLobby;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.arcadeui.MultiplayerLobby;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;

public class LobbyListenerTest {

	@Before
	public void Setup() {
		CreateMatchRequest cmr1 = new CreateMatchRequest();
		cmr1.gameId = "1";
		cmr1.hostConnection = null;
	}

}
