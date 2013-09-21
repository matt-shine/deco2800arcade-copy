package deco2800.arcade.snakeLadder;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.snakeLadderModel.*;

/**
 * This is the main class for game snake&Ladder
 * @author s4310055,s43146400,s43146884,s4243072
 *
 */

@ArcadeGame(id="snakeLadder")
public class SnakeLadder extends GameClient {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
    public GamePlayer gamePlayer;
    public AIPlayer AIPlayer;
    private GameMap map;
   
	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public GameState gameState;
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	private ArrayList<Label> userLabels = new ArrayList<Label>(); //labels for users
    private ArrayList<Label> scoreLabels = new ArrayList<Label>(); 

	private Stage stage;
	private Skin skin;
	private BitmapFont font;
	private TextButton diceButton;
	public String statusMessage;

	private Label diceLabel;
	private Dice dice;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;
	
	// haku add
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;

	public SnakeLadder(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
        this.networkClient = networkClient; //this is a bit of a hack
	}

	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		//add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void show() {
				
			}
        });
        
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 800);
		batch = new SpriteBatch();
		
		//creating level loading background board and initializing the rule mapping
		map =new GameMap();
		map.ini();
		//loading game map
		map.loadMap("maps/lvl1.txt");
		
		// create the game player
		gamePlayer = new GamePlayer();

		// create the AI player
		AIPlayer=new AIPlayer();
		
		font = new BitmapFont();
		font.setScale(2);
		//Initialise the game state
		//gameState = GameState.READY;
		gameState = new ReadyState();
		statusMessage = "Click to start!";
		
		
		//creating the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
      //rendering scoreboard UI
      		renderScoreBoard();
        
        setDice(new Dice());
        
        //TODO: button should be disabled when its others player turn or when player is on the move
       // diceButton listener for when the button is pressed
//        diceButton.addListener(new ChangeListener() {
//            public void changed (ChangeEvent event, Actor actor) {
//            	dice.rollDice();
//            }
//        });
 
    
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render() {	
		//Black background
				Gdx.gl.glClearColor(0, 0, 0, 1);
			    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			    
			    //adding stage
			    stage.act(Gdx.graphics.getDeltaTime());
		        stage.draw();
		// tell the camera to update its matrices.
		camera.update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

	    //Begin batch
	    batch.begin();
		// render map for this level
		map.renderMap(batch);
		
		getDice().renderDice(batch);
		gamePlayer.renderPlayer(batch);
		AIPlayer.renderPlayer(batch);
		
		
		
		 //If there is a current status message (i.e. if the game is in the ready or gameover state)
	    // then show it in the middle of the screen
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, 200, 300);
	    	if (gameState instanceof GameOverState) 
	    	{
	    		font.draw(batch, statusMessage+" Click to exit", 200, 300);
	    	}
	    }
		batch.end();
		
		 handleInput();
		
		super.render();
		
	}

	/**
	 * Handle player input from mouse click
	 */
	private void handleInput() {
//		switch(gameState) {		    
//		    case READY: //Ready to start a new point		    			    	
//		    case INPROGRESS: 		    	
//		    case GAMEOVER: //The game has been won, wait to exit		    	
//		    }
		gameState.handleInput(this);
	}
	
	public void stopPoint() {
		this.updateScore(gamePlayer);
		gamePlayer.reset();
		AIPlayer.reset();
		// If we've reached the victory point then update the display
		if (gamePlayer.getBounds().x <= (60-20f) && gamePlayer.getBounds().y >= (540)) {			   
			gameState = new GameOverState();
		    //Update the game state to the server
		    //networkClient.sendNetworkObject(createScoreUpdate());
		} 
		if (AIPlayer.getBounds().x<=(60-20f)&& AIPlayer.getBounds().y>=540){
			gameState = new GameOverState();
		}
		else {
			// No winner yet, get ready for another point
			gameState = new ReadyState();
			statusMessage = "Throw the dice again";
		}

	}
	
	public void startPoint() {
		gamePlayer.initializeVelocity();
		getDice().rollDice();	
		AIPlayer.initializeVelocity();
		gameState = new InProgressState();
		statusMessage = null;
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width*2, height*2);
	}

	@Override
	public void resume() {
		super.resume();
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}
	
	/*
	 * Rendering scoreboard UI
	 */
	public void renderScoreBoard(){
		//setting up the skin for the layout
				skin = new Skin();
				Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		        pixmap.setColor(Color.WHITE);
		        pixmap.fill();
		        skin.add("white", new Texture(pixmap));
		        
				skin.add("default", new BitmapFont());
				//skin for label
				Label.LabelStyle labelStyle = new Label.LabelStyle();
		        labelStyle.font = skin.getFont("default");
		        skin.add("default", labelStyle);		
				
		        //skin for textbutton or dice button
				TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		        textButtonStyle.checked = skin.newDrawable("white", Color.WHITE);
		        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		        textButtonStyle.font = skin.getFont("default");
		        skin.add("default", textButtonStyle);
		        
		        //setting the dice button
		        diceButton = new TextButton("Roll the dice", skin);
		        
		        //labels for scores of each user
		        
		        //setting players name and score
		        for(int i = 0; i < players.length; i++){
		        	userLabels.add(new Label (players[i], skin));
		        	scoreLabels.add(new Label ("0", skin)); //This is just setting the initial score which is 0
			    }
		        		        
		        
		        //table to place all the GUI
		        Table table = new Table();
		        table.setFillParent(true);
		        stage.addActor(table);
		        table.top().left();
		        
		        //adding player name
		        table.add(new Label("Players' Name", skin)).width(200).top().left();
		        for(int i = 0; i < players.length; i++){
		        	table.add(userLabels.get(i)).width(200).top().left();
			    } 
		        table.row();
		        //add player score
		        table.add(new Label("Players' Score", skin)).width(200).top().left();
		        for(int i = 0; i < players.length; i++){
		        	table.add(scoreLabels.get(i)).width(200).top().left();
			    } 
		        table.row();     
		        //adding dice button to GUI
		        table.add(diceButton).width(100).height(50).pad(10);
		        
		        table.row();
	}
	
	/*
	 * Updating score label
	 */
	private void updateScore(GamePlayer gp){
		//System.out.println(gp.newposition());
		String rule = this.getMap().getTileList()[gp.newposition()].getRule();
		if (isScore(rule))
			gp.setScore(Integer.parseInt(rule));
		System.out.println("This player's score: "+ gp.getScore());
		//TODO: should update the scoreLabel depending on the gamePlayer index
		this.scoreLabels.get(0).setText(""+gp.getScore());
	}
	
	/*
	 * Check if the rule is related to score or not
	 */
	private boolean isScore (String rule) {
	    try { 
	        Integer.parseInt(rule); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}

