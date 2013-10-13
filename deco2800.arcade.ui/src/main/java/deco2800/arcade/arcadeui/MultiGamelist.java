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

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import java.util.*;
import com.badlogic.gdx.Input.Keys;

public class MultiGamelist implements Screen {
	
	private class FrontPageStage extends Stage {}
	
    private Skin skin;
    private FrontPageStage stage;
	
    private float funds;
    private int tokens;
    
    private boolean bclicked;
    
    Texture bg;
    Sprite bgSprite;
    SpriteBatch batch;
	
		private ArcadeUI arcadeUI;
		
		private MultiplayerLobby lobby;
	ArrayList<ActiveMatchDetails> matches;
	
	public MultiGamelist(ArcadeUI ui) {
		arcadeUI = ui;
	}
    
    public MultiGamelist() {
    	
        
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));
        stage = new FrontPageStage();
               
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
        
        bg = new Texture("homescreen_bg.png");
        bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bgSprite = new Sprite(bg);
        batch = new SpriteBatch();
  
        //Text Buttons
        final TextButton chessButton = new TextButton("Chess", skin, "green");
        
        final TextButton chatButton = new TextButton("Chat", skin);
        final TextButton towerButton = new TextButton("Tower Defence", skin, "magenta");
        final TextButton pongButton = new TextButton("Pong", skin, "blue");
        
        
        final int bWidth = 300;
        final int bHeight = 300;
        final int bX = 150;
        final int bY = 220;
        final int enlarge = 50;
        final int bX2= bX + bWidth + (enlarge);
        final int bX3= bX + 2*(bWidth + enlarge);
        //make button sizes and positioning
        
      
        pongButton.setSize(bWidth, bHeight);        
        pongButton.setPosition(bX, bY);
       
        towerButton.setSize(bWidth, bHeight);
        towerButton.setPosition(bX2, bY);
        
        chessButton.setSize(bWidth, bHeight);
        chessButton.setPosition(bX3, bY);
        
        
        //adding panel for top and bottom bar
        final Table topBox = new Table();
        final Table bottomBox = new Table();
        
        //set panel sizes and positions
        topBox.setSize(1279, 60);
        topBox.setPosition(1, 659);
        topBox.setColor(255, 255, 255, 1);
        
        bottomBox.setSize(1279, 60);
        bottomBox.setPosition(1, 1);
        bottomBox.setColor(255, 255, 255, 1);
        
        //adding to stage
        stage.addActor(topBox);
        stage.addActor(bottomBox);
        
        //this somehow makes it show up
        topBox.debug();
        bottomBox.debug();
        
        pongButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
           		pongButton.setSize(bWidth + enlarge,  bHeight + enlarge);
           		pongButton.setPosition(bX -(enlarge/2), bY-(enlarge/2));
           		pongButton.setText(null);
               	pongButton.setText("Pong");
            		
            		 			     			
           
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	pongButton.setSize(bWidth, bHeight);
            	pongButton.setPosition(bX, bY);
            	pongButton.setText(null);
            	pongButton.setText("Pong");
            }}));   
        
	    
		pongButton.addListener((new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	//ArcadeSystem.login("pong");
		    	//bclicked = true;
		    	System.out.println("Pong clicked");
		    }
		})); 
        
        towerButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	    	
           		 towerButton.setSize(bWidth + enlarge,  bHeight + enlarge);
           		 towerButton.setPosition(bX2 -(enlarge/2), bY-(enlarge/2));	
           		 towerButton.setText(null);
           		 towerButton.setText("Tower Defence");
            	 
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	towerButton.setSize(bWidth, bHeight);
            	towerButton.setPosition(bX2, bY);
            	towerButton.setText(null);
            	towerButton.setText("Tower Defence");
            }}));   
        
	    towerButton.addListener((new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	//ArcadeSystem.login("Tower Defence");
	        	//bclicked = true;
	        	System.out.println("Tower Defence clicked");
	        }
	    })); 
	    
        
        chessButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			chessButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		chessButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		chessButton.setText(null);
         		chessButton.setText("Chess");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	chessButton.setSize(bWidth, bHeight);
                chessButton.setPosition(bX3, bY);
                chessButton.setText(null);
     			chessButton.setText("Chess");
            }}));   
        
        chessButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Chess clicked");	
            	
				
				
            }
        })); 
        
		
        
        //adding to stage
        stage.addActor(pongButton);
        stage.addActor(towerButton);
        stage.addActor(chessButton);
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
        
        
        
		if (bclicked == true) {
        	System.out.println("going to arcadeui");
	    	ArcadeSystem.goToGame("arcadeui");
	    }
    	
        
        
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