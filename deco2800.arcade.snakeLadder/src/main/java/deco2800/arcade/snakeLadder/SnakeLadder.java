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
	private Tile[] tileList; 
    private GamePlayer gamePlayer;
    private Level lvl;
    private Texture ladder;
    private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	GameState gameState;
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]

	private ShapeRenderer shapeRenderer;
	private Stage stage;
	private Skin skin;
	private BitmapFont font;
	private TextButton diceButton;
	private String statusMessage;
	
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
		//test
		ladder =  new Texture(Gdx.files.classpath("assets/arrow.png"));
		
		//creating level loading background board and initializing the rule mapping
		lvl =new Level1();
		lvl.ini();
	
		//initialize the map size for this level 
		tileList = lvl.iniMapSize();
		//loading game map
		lvl.loadMap(tileList, "assets/lvl1.txt");
	
		// create the game player
		gamePlayer = new GamePlayer();

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		//Initialise the game state
		gameState = GameState.READY;
		statusMessage = "Click to start!";
		
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
        
        ArrayList<Label> userLabels = new ArrayList<Label>(); //labels for users
        ArrayList<Label> scoreLabels = new ArrayList<Label>(); //labels for scores of each user
        
        //setting players name and score
        for(int i = 0; i < players.length; i++){
        	userLabels.add(new Label (players[i], skin));
        	scoreLabels.add(new Label (""+gamePlayer.getScore(), skin)); //This is just returning player 1 score
	    }
        
        //creating the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
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
        
        //label for the dice roll
        //diceLabel = new Label ("x",skin);        
        //table.add(diceLabel).width(100).height(50).pad(10);
        dice = new Dice();
        
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
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		 //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    //Begin batch
	    batch.begin();
		// render map for this level
		lvl.renderMap(tileList, batch);
		//test
		batch.draw(ladder,tileList[5].getCoorX(), tileList[5].getCoorY());
		dice.renderDice(batch);

		
		 //If there is a current status message (i.e. if the game is in the ready or gameover state)
	    // then show it in the middle of the screen
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, 200, 300);
	    	if (gameState == GameState.GAMEOVER) {
	    		font.draw(batch, statusMessage+" Click to exit", 200, 300);
	    	}
	    }
		batch.end();
		//Render gamePlayer 
		gamePlayer.render(shapeRenderer);
		 //End drawing of shapes
	    shapeRenderer.end();

		
		 switch(gameState) {
		    
		    case READY: //Ready to start a new point
		    	if (Gdx.input.isTouched()) {
		    		startPoint();
		    		gamePlayer.getDnumber(dice.getDiceNumber());
		    	}
		    	break;
		    	
		    case INPROGRESS: 
		    	gamePlayer.move(Gdx.graphics.getDeltaTime());
//		    	int a= dice.getDiceNumber();
//		    	int position=0;
//		    	while(0<=gamePlayer.bounds.x &&gamePlayer.bounds.x<=(600-20f)&&gamePlayer.bounds.y<=540)
//		    	{
//		    		
//		    		position+=a;
//		    	}
				if(Math.abs(tileList[gamePlayer.newposition()].getCoorX() - gamePlayer.bounds.x) <(1f)&&Math.abs(tileList[gamePlayer.newposition()].getCoorY() - gamePlayer.bounds.y) <(1f))
			    {
				    	stopPoint();
			    }

		    	//If the player reaches the end of each line , move up to another line
		    	if (gamePlayer.bounds.x >= (600-20f) || gamePlayer.bounds.x <=0){
		    		gamePlayer.moveUp();
		    	}	    	
		    	//If the ball gets to the left edge then player 2 wins
		    	if (gamePlayer.bounds.x <= (60-20f) && gamePlayer.bounds.y >= (540)) {
		    		gamePlayer.reset();
		    		statusMessage = "You Win! ";
		    		gameState = GameState.GAMEOVER;
		    		//statusMessage = "Win!";
		    				//+ "Click to exit!";
		    		//endPoint(1);
		    	}
		    	break;
		    case GAMEOVER: //The game has been won, wait to exit
		    	if (Gdx.input.isTouched()) {
		    		gameOver();
		    		ArcadeSystem.goToGame(ArcadeSystem.UI);
		    	}
		    	break;
		    }
		
		super.render();
		
	}
	
	public void stopPoint() {
		gamePlayer.reset();
		// If we've reached the victory point then update the display
		if (gamePlayer.bounds.x <= (60-20f) && gamePlayer.bounds.y >= (540)) {	
		   
		    gameState = GameState.GAMEOVER;
		    //Update the game state to the server
		    //networkClient.sendNetworkObject(createScoreUpdate());
		   
		} else {
			// No winner yet, get ready for another point
			gameState = GameState.READY;
			statusMessage = "Throw the dice again";
		}

	}
	
	private void startPoint() {
		// TODO Auto-generated method stub
		gamePlayer.initializeVelocity();
		dice.rollDice();	
		gameState = GameState.INPROGRESS;
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
	
}
