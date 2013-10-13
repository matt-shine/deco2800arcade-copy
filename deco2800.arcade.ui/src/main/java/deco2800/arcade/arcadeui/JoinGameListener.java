package deco2800.arcade.arcadeui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class JoinGameListener extends ChangeListener {
	
	int matchId;
	MultiplayerLobby lobby;
	
	public JoinGameListener(int matchId, MultiplayerLobby lobby) {
		this.matchId = matchId;
		this.lobby = lobby;
	}
	
	public void changed(ChangeEvent event, Actor actor) {
		System.out.println("Clicked: " + matchId);
		lobby.joinGame(matchId);
	}
	
}
