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

import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import java.util.*;
import com.badlogic.gdx.Input.Keys;

public class MultiGamelist2 implements Screen {
	
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
	/*
	public MultiGamelist2(ArcadeUI ui) {
		this.arcadeUI = ui;
	}
    */
    public MultiGamelist2(ArcadeUI ui) {
    		
        arcadeUI = ui;
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
		
		final TextButton chessButton2 = new TextButton("Chess", skin, "green");
        final TextButton towerButton2 = new TextButton("Tower Defence", skin, "magenta");
        final TextButton pongButton2 = new TextButton("Pong", skin, "blue");
		
		final TextButton raidenButton = new TextButton("Raiden", skin, "green");
		final TextButton snakeButton = new TextButton("Snakes & Ladders", skin, "magenta");
        final TextButton tictacButton = new TextButton("TicTacToe", skin, "blue");
		
		final TextButton burnButton = new TextButton("Burning Skies", skin, "green");
		final TextButton checkersButton = new TextButton("Checkers", skin, "magenta");
        final TextButton connect4Button = new TextButton("Connect 4", skin, "blue");
		
		final TextButton pacmanButton = new TextButton("Pacman", skin, "green");
		final TextButton deerButton = new TextButton("Deer Forest", skin, "magenta");
        final TextButton jungleButton = new TextButton("Jungle Jump", skin, "blue");
		
		final TextButton mixmazeButton = new TextButton("Mix Maze", skin, "green");
		final TextButton landButton = new TextButton("Land Invaders", skin, "magenta");

		
        final int bWidth = 300;
        final int bHeight = 300;
        final int bX = 150;
        final int bY = 220;
        final int enlarge = 50;
        final int bX2= bX + bWidth + (enlarge);
        final int bX3= bX + 2*(bWidth + enlarge);
        
        
		final Table listtable = new Table();
		listtable.setFillParent(true);
		stage.addActor(listtable);

      
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
		
		TextButton button3 = new TextButton("Return to Lobby", skin);
		TextButton button4 = new TextButton("<", skin);
		TextButton button5 = new TextButton(">", skin);
		
		table.add(button4).width(60).height(40).padTop(10).padLeft(310);
		table.add(button3).width(300).height(40).padTop(600).padRight(390).padLeft(390);
		table.add(button5).width(60).height(40).padTop(10).padRight(290);
        
		/*<--SCROLLER START-->*/
		
		//Scroll Right
		button5.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
			
            	if (chessButton.getStage() != null || chessButton2.getStage() != null){

				listtable.clear();

				raidenButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			raidenButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		raidenButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		raidenButton.setText(null);
         		raidenButton.setText("Raiden");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	raidenButton.setSize(bWidth, bHeight);
                raidenButton.setPosition(bX3, bY);
                raidenButton.setText(null);
     			raidenButton.setText("Raiden");
            }}));   
        
        raidenButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Raiden clicked");	
	
            }
        })); 
		
		snakeButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			snakeButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		snakeButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		snakeButton.setText(null);
         		snakeButton.setText("Snakes & Ladders");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	snakeButton.setSize(bWidth, bHeight);
                snakeButton.setPosition(bX3, bY);
                snakeButton.setText(null);
     			snakeButton.setText("Snakes & Ladders");
            }}));   
        
        snakeButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Snakes & Ladders clicked");	
            	
				
				
            }
        })); 
		
		tictacButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			tictacButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		tictacButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		tictacButton.setText(null);
         		tictacButton.setText("TicTacToe");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	tictacButton.setSize(bWidth, bHeight);
                tictacButton.setPosition(bX3, bY);
                tictacButton.setText(null);
     			tictacButton.setText("TicTacToe");
            }}));   
        
        tictacButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("TicTacToe clicked");	
	
            }
        })); 
		
		listtable.add(raidenButton).width(300).height(300);
		listtable.add(snakeButton).width(300).height(300);
		listtable.add(tictacButton).width(300).height(300);
				
				}
				
				
				else if (raidenButton.getStage() != null){

				listtable.clear();

				burnButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			burnButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		burnButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		burnButton.setText(null);
         		burnButton.setText("Burning Skies");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	burnButton.setSize(bWidth, bHeight);
                burnButton.setPosition(bX3, bY);
                burnButton.setText(null);
     			burnButton.setText("Burning Skies");
            }}));   
        
        burnButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Burning Skies clicked");	
            	
				
				
            }
        })); 
		
		checkersButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			checkersButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		checkersButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		checkersButton.setText(null);
         		checkersButton.setText("Checkers");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	checkersButton.setSize(bWidth, bHeight);
                checkersButton.setPosition(bX3, bY);
                checkersButton.setText(null);
     			checkersButton.setText("Checkers");
            }}));   
        
        checkersButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Checkers clicked");	
            	
				
				
            }
        })); 
		
		connect4Button.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			connect4Button.setSize(bWidth + enlarge,  bHeight + enlarge);
         		connect4Button.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		connect4Button.setText(null);
         		connect4Button.setText("Connect 4");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	connect4Button.setSize(bWidth, bHeight);
                connect4Button.setPosition(bX3, bY);
                connect4Button.setText(null);
     			connect4Button.setText("Connect 4");
            }}));   
        
        connect4Button.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Connect4 clicked");	

            }
        })); 
		
		listtable.add(burnButton).width(300).height(300);
		listtable.add(checkersButton).width(300).height(300);
		listtable.add(connect4Button).width(300).height(300);
				
				}
				
				
				else if (burnButton.getStage() != null){

				listtable.clear();

				pacmanButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			pacmanButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		pacmanButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		pacmanButton.setText(null);
         		pacmanButton.setText("Pacman");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	pacmanButton.setSize(bWidth, bHeight);
                pacmanButton.setPosition(bX3, bY);
                pacmanButton.setText(null);
     			pacmanButton.setText("Pacman");
            }}));   
        
        pacmanButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Pacman clicked");	
            	
				
				
            }
        })); 
		
		deerButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			deerButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		deerButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		deerButton.setText(null);
         		deerButton.setText("Deer Jungle");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	deerButton.setSize(bWidth, bHeight);
                deerButton.setPosition(bX3, bY);
                deerButton.setText(null);
     			deerButton.setText("Deer Jungle");
            }}));   
        
        deerButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Deer Jungle clicked");	
            	
				
				
            }
        })); 
		
		jungleButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			jungleButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		jungleButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		jungleButton.setText(null);
         		jungleButton.setText("Jungle Jump");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	jungleButton.setSize(bWidth, bHeight);
                jungleButton.setPosition(bX3, bY);
                jungleButton.setText(null);
     			jungleButton.setText("Jungle Jump");
            }}));   
        
        jungleButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Jungle Jump clicked");	
            	
				
				
            }
        })); 
		
		listtable.add(pacmanButton).width(300).height(300);
		listtable.add(deerButton).width(300).height(300);
		listtable.add(jungleButton).width(300).height(300);
				
				}
				
				
				
				else if (pacmanButton.getStage() != null){

				listtable.clear();

			mixmazeButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			mixmazeButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		mixmazeButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		mixmazeButton.setText(null);
         		mixmazeButton.setText("Mix Maze");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	mixmazeButton.setSize(bWidth, bHeight);
                mixmazeButton.setPosition(bX3, bY);
                mixmazeButton.setText(null);
     			mixmazeButton.setText("Mix Maze");
            }}));   
        
        mixmazeButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Mixmaze clicked");	
            	
				
				
            }
        })); 
		
		landButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			landButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		landButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		landButton.setText(null);
         		landButton.setText("Land Invaders");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	landButton.setSize(bWidth, bHeight);
                landButton.setPosition(bX3, bY);
                landButton.setText(null);
     			landButton.setText("Land Invaders");
            }}));   
        
        landButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Land Invaders clicked");	
            	
				
				
            }
        })); 
		
		listtable.add(mixmazeButton).width(300).height(300);
		listtable.add(landButton).width(300).height(300);
		//listtable.add(jungleButton).width(300).height(300);
				
				}
				
				else if (mixmazeButton.getStage() != null){
				//Do Nothing
				}

            }
        });
		
		
		//Scroll Left
		button4.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	if (chessButton.getStage() != null || chessButton2.getStage() != null){
				//Do Nothing
				}
				
				if (raidenButton.getStage() != null){
				
				listtable.clear();
				
	pongButton2.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
           		pongButton2.setSize(bWidth + enlarge,  bHeight + enlarge);
           		pongButton2.setPosition(bX -(enlarge/2), bY-(enlarge/2));
           		pongButton2.setText(null);
               	pongButton2.setText("Pong");

            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	pongButton2.setSize(bWidth, bHeight);
            	pongButton2.setPosition(bX, bY);
            	pongButton2.setText(null);
            	pongButton2.setText("Pong");
            }}));   
        
	    
		pongButton2.addListener((new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	//ArcadeSystem.login("pong");
		    	//bclicked = true;
		    	System.out.println("Pong clicked");
				createPongMatch();
		    }
		})); 
        
        towerButton2.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	    	
           		 towerButton2.setSize(bWidth + enlarge,  bHeight + enlarge);
           		 towerButton2.setPosition(bX2 -(enlarge/2), bY-(enlarge/2));	
           		 towerButton2.setText(null);
           		 towerButton2.setText("Tower Defence");
            	 
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	towerButton2.setSize(bWidth, bHeight);
            	towerButton2.setPosition(bX2, bY);
            	towerButton2.setText(null);
            	towerButton2.setText("Tower Defence");
            }}));   
        
	    towerButton2.addListener((new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	//ArcadeSystem.login("Tower Defence");
	        	//bclicked = true;
	        	System.out.println("Tower Defence clicked");
	        }
	    })); 
	    
        
        chessButton2.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			chessButton2.setSize(bWidth + enlarge,  bHeight + enlarge);
         		chessButton2.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		chessButton2.setText(null);
         		chessButton2.setText("Chess");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	chessButton2.setSize(bWidth, bHeight);
                chessButton2.setPosition(bX3, bY);
                chessButton2.setText(null);
     			chessButton2.setText("Chess");
            }}));   
        
        chessButton2.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Chess clicked");	
	
				createChessMatch();
				System.out.println("Chess Match Created.");
				
            }
        })); 
		
		listtable.add(pongButton2).width(300).height(300);
		listtable.add(towerButton2).width(300).height(300);
		listtable.add(chessButton2).width(300).height(300);
				
				}
				
				else if (burnButton.getStage() != null){

				listtable.clear();

				raidenButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			raidenButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		raidenButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		raidenButton.setText(null);
         		raidenButton.setText("Raiden");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	raidenButton.setSize(bWidth, bHeight);
                raidenButton.setPosition(bX3, bY);
                raidenButton.setText(null);
     			raidenButton.setText("Raiden");
            }}));   
        
        raidenButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Raiden clicked");	
            	
				
				
            }
        })); 
		
		snakeButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			snakeButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		snakeButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		snakeButton.setText(null);
         		snakeButton.setText("Snakes & Ladders");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	snakeButton.setSize(bWidth, bHeight);
                snakeButton.setPosition(bX3, bY);
                snakeButton.setText(null);
     			snakeButton.setText("Snakes & Ladders");
            }}));   
        
        snakeButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Snakes & Ladders clicked");	
            	
				
				
            }
        })); 
		
		tictacButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			tictacButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		tictacButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		tictacButton.setText(null);
         		tictacButton.setText("TicTacToe");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	tictacButton.setSize(bWidth, bHeight);
                tictacButton.setPosition(bX3, bY);
                tictacButton.setText(null);
     			tictacButton.setText("TicTacToe");
            }}));   
        
        tictacButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("TicTacToe clicked");	
            	
				
				
            }
        })); 
		
		listtable.add(raidenButton).width(300).height(300);
		listtable.add(snakeButton).width(300).height(300);
		listtable.add(tictacButton).width(300).height(300);
				
				}
				
				
				else if (mixmazeButton.getStage() != null){

				listtable.clear();

						pacmanButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			pacmanButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		pacmanButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		pacmanButton.setText(null);
         		pacmanButton.setText("Pacman");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	pacmanButton.setSize(bWidth, bHeight);
                pacmanButton.setPosition(bX3, bY);
                pacmanButton.setText(null);
     			pacmanButton.setText("Pacman");
            }}));   
        
        pacmanButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Pacman clicked");	
            	
				
				
            }
        })); 
		
		deerButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			deerButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		deerButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		deerButton.setText(null);
         		deerButton.setText("Deer Jungle");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	deerButton.setSize(bWidth, bHeight);
                deerButton.setPosition(bX3, bY);
                deerButton.setText(null);
     			deerButton.setText("Deer Jungle");
            }}));   
        
        deerButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Deer Jungle clicked");	
            	
				
				
            }
        })); 
		
		jungleButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			jungleButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		jungleButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		jungleButton.setText(null);
         		jungleButton.setText("Jungle Jump");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	jungleButton.setSize(bWidth, bHeight);
                jungleButton.setPosition(bX3, bY);
                jungleButton.setText(null);
     			jungleButton.setText("Jungle Jump");
            }}));   
        
        jungleButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Jungle Jump clicked");	
            	
				
				
            }
        })); 
		
		listtable.add(pacmanButton).width(300).height(300);
		listtable.add(deerButton).width(300).height(300);
		listtable.add(jungleButton).width(300).height(300);

				}

							else if (pacmanButton.getStage() != null){

				listtable.clear();

				burnButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			burnButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		burnButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		burnButton.setText(null);
         		burnButton.setText("Burning Skies");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	burnButton.setSize(bWidth, bHeight);
                burnButton.setPosition(bX3, bY);
                burnButton.setText(null);
     			burnButton.setText("Burning Skies");
            }}));   
        
        burnButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Burning Skies clicked");	
            	
				
				
            }
        })); 
		
		checkersButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			checkersButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		checkersButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		checkersButton.setText(null);
         		checkersButton.setText("Checkers");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	checkersButton.setSize(bWidth, bHeight);
                checkersButton.setPosition(bX3, bY);
                checkersButton.setText(null);
     			checkersButton.setText("Checkers");
            }}));   
        
        checkersButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Checkers clicked");	
            	
				
				
            }
        })); 
		
		connect4Button.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			connect4Button.setSize(bWidth + enlarge,  bHeight + enlarge);
         		connect4Button.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		connect4Button.setText(null);
         		connect4Button.setText("Connect 4");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	connect4Button.setSize(bWidth, bHeight);
                connect4Button.setPosition(bX3, bY);
                connect4Button.setText(null);
     			connect4Button.setText("Connect 4");
            }}));   
        
        connect4Button.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	//ArcadeSystem.login("chess");
            	System.out.println("Connect4 clicked");	
            	
				
				
            }
        })); 
		
		listtable.add(burnButton).width(300).height(300);
		listtable.add(checkersButton).width(300).height(300);
		listtable.add(connect4Button).width(300).height(300);
				
				}
				
            }
        });

       
		
		/*<--SCROLLER END-->*/
		
		
		// Return to lobby event listener
		 button3.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
				ArcadeSystem.setMatchMaking2(false);
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());
		
            }
        });
		
       
		
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
				createPongMatch();
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
            	
				createChessMatch();
				System.out.println("Chess Match Created.");
				
            }
        })); 
		
		
		listtable.add(pongButton).width(300).height(300);
		listtable.add(towerButton).width(300).height(300);
		listtable.add(chessButton).width(300).height(300);
		listtable.row();
	
		
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
	
	private void createChessMatch() {
		CreateMatchRequest request = new CreateMatchRequest();
    	request.gameId = "chess";
    	request.playerID = arcadeUI.getPlayer().getID();
    	arcadeUI.getNetworkClient().sendNetworkObject(request);
	}
	
	private void createPongMatch() {
		CreateMatchRequest request = new CreateMatchRequest();
    	request.gameId = "pong";
    	request.playerID = arcadeUI.getPlayer().getID();
    	arcadeUI.getNetworkClient().sendNetworkObject(request);
	}
	
}