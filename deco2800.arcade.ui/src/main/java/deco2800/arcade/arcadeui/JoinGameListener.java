package deco2800.arcade.arcadeui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class JoinGameListener extends ChangeListener {
	
	int matchNum;
	MultiplayerLobby lobby;
	
	public JoinGameListener(int matchNum, MultiplayerLobby lobby) {
		this.matchNum = matchNum;
		this.lobby = lobby;
	}
	
	public void changed(ChangeEvent event, Actor actor) {
		System.out.println("Clicked: " + matchNum);
		lobby.joinGame(matchNum);
	}
	
}
