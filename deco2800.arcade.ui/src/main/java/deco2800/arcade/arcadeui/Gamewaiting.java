package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer.*;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import java.util.*;
import com.badlogic.gdx.Input.Keys;


public class Gamewaiting implements Screen {
	
	private class FrontPageStage extends Stage {}
	
    private Skin skin;
    private FrontPageStage stage;
	
    private float funds;
    private int tokens;
    boolean multiplayerEnabled;
    private boolean bclicked;
	private int scrollcheck;
    
    Texture bg;
    Sprite bgSprite;
    SpriteBatch batch;
	
		private ArcadeUI arcadeUI;
		
		private MultiplayerLobby lobby;
	ArrayList<ActiveMatchDetails> matches;
	
	public Gamewaiting(ArcadeUI ui) {
		arcadeUI = ui;
	}

    public Gamewaiting() {
    	
        
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));
        stage = new FrontPageStage();
               
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
		
		Table table2 = new Table();
        table2.setFillParent(true);
        stage.addActor(table2);

        bg = new Texture("homescreen_bg.png");
        bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bgSprite = new Sprite(bg);
        batch = new SpriteBatch();
  
		TextButton button3 = new TextButton("Return to Lobby", skin);
		Label label = new Label("Waiting for a Match...", skin);
		
		table2.add(label).width(300).height(40).padTop(15).padLeft(85);
		
		table.add(button3).width(300).height(40).padTop(600);
		
		//Timer code coming soon...
		/*
		if (button3.getStage() != null){
		
		TimerTask task = new RunMeTask();
 
    	Timer timer = new Timer();
    	timer.schedule(task, 0,30000);
		   
   }    
   */

		// Return to lobby event listener
		 button3.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	dispose();
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());
				

            }
        });

		
    }
	

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bgSprite.draw(batch);
		batch.end();
		
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Table.drawDebug(stage);  // Shows table debug lines
 
        
	}
	
	@Override
	public void dispose() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
        stage.dispose();
        skin.dispose();
	}
	
	@Override
	public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void resize(int arg0, int arg1) {
	}
}