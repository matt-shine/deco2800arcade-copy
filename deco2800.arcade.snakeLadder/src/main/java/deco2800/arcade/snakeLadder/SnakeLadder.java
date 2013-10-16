package deco2800.arcade.snakeLadder;

import java.io.IOException;
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
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.snakeLadderGameState.GameOverState;
import deco2800.arcade.snakeLadderGameState.GameState;
import deco2800.arcade.snakeLadderGameState.WaitingState;
import deco2800.arcade.snakeLadderModel.*;

/**
 * This is the main class for game snake&Ladder
 * @author s4310055,s4314640,s43146884,s4243072
 *
 */

@ArcadeGame(id="snakeLadder")
public class SnakeLadder extends GameClient {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
    public GamePlayer[] gamePlayers = new GamePlayer[2];
    private GameMap map;

	public GameState gameState;
	private ArrayList<Label> userLabels = new ArrayList<Label>(); //labels for users
    private ArrayList<Label> scoreLabels = new ArrayList<Label>();
    private ArrayList<Dice> dices = new ArrayList<Dice>();

	private Stage stage;
	private Skin skin;
	private BitmapFont font;
	public TextButton diceButton;
	public String statusMessage;
	private Dice dice;
	private Dice diceAI;
	private int turn=0;
	private HashMap<String,RuleMapping> ruleMapping = new HashMap<String,RuleMapping>();
	//NetworkClient for communicating with the server
	private NetworkClient networkClient;
	//use AchievementClient
	private AchievementClient achievementClient;
	public HighscoreClient player1;
	private HighscoreClient hsc;
	private int highestScoreNum = 5;

	public SnakeLadder(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		gamePlayers[0] = new GamePlayer(this.player.getUsername(), false);
		gamePlayers[1] = new GamePlayer("AI Player", true);
		this.networkClient = networkClient;
		player1 = new HighscoreClient(this.player.getUsername(), "snakeLadder", this.networkClient);
		hsc = new HighscoreClient("snakeLadder", networkClient);
		dumpingScores();
	}
	
	//constructor for testing
	public SnakeLadder(Player player, NetworkClient networkClient, String username) {
		super(player, networkClient);
		gamePlayers[0] = new GamePlayer(username, false);
		gamePlayers[1] = new GamePlayer("AI Player", true);
	}
	
	//constructor for testing
	public SnakeLadder(Player player, NetworkClient networkClient, String username,FileHandle xmlFile, FileHandle mapFile) {
		super(player, networkClient);
		gamePlayers[0] = new GamePlayer(username, false);
		gamePlayers[1] = new GamePlayer("AI Player", true);
		map = new GameMap();
		//initialize the rules from xml file
		ruleMapping = RuleMapping.iniRuleMapping(xmlFile);
		//loading game map
		try {
			map.populateTileListFromMapFile(mapFile,getRuleMapping());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getturns() {
		return this.turn%2;
	}

	public ArrayList<Label> getScoreLabels() {
		return scoreLabels;
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {        
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 800);
		batch = new SpriteBatch();
		
		//initialize the rules from xml file
		ruleMapping = RuleMapping.iniRuleMapping(Gdx.files.classpath("ruleMapping.xml"));
		//creating level loading background board and initializing the rule mapping
		map =new GameMap(Gdx.files.classpath("images/board.png"));
		//loading game map
		map.loadMap(Gdx.files.classpath("maps/lvl1.txt"),getRuleMapping());
		
		//loading the icon for each player
		gamePlayers[0].setPlayerTexture("player.png");
		gamePlayers[1].setPlayerTexture("AI.png");
		
		font = new BitmapFont();
		font.setScale(2);
		
		//Initialise the game state
		//gameState = GameState.READY;
		gameState = new WaitingState();
		statusMessage = "Roll the dice to start!";
		
		
		//creating the stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        //rendering scoreboard UI
  		renderScoreBoard();
        
  		for (int i = 0; i < gamePlayers.length; i++){
  			dices.add(new Dice());
  		}
  		
        
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
		
		
		for (int i = 0; i < gamePlayers.length; i++){
  			getDice(i).renderDice(batch, i);
  		}
		//getDice().renderDice(batch,1);
		//getDiceAI().renderDice(batch,2);
		for(GamePlayer gamePlayer: gamePlayers)
		{
			gamePlayer.renderPlayer(batch);
		}
		
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
	 * Handle player input from button click
	 */
	private void handleInput() {
		//use gameState to handle user input
		gameState.handleInput(this);
	}
	

	public int taketurns() {
		turn++;
		return this.turn;
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
	
	public Dice getDice(int num){
		return dices.get(num);
	}
	
	public void setDice(Dice dice, int num){
		dices.set(num, dice);
	}

	
	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	/***
	 * Rendering score board UI on top of the screen
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
        for(int i = 0; i < gamePlayers.length; i++){
        	userLabels.add(new Label (gamePlayers[i].getPlayerName(), skin));
        	getScoreLabels().add(new Label ("0", skin)); //This is just setting the initial score which is 0
	    }
        		        
        
      //table to place all the GUI
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.padLeft((gamePlayers.length+1)*100).padBottom(200-(highestScoreNum*25)-45);
        
        //adding player name
        table.add(new Label("Players' Name", skin)).width(100).top().left();
        for(int i = 0; i < gamePlayers.length; i++){
        	table.add(userLabels.get(i)).width(100).top().left().spaceLeft(10);
	    } 
        table.row();
        //add player score
        table.add(new Label("Players' Score", skin)).width(100).top().left();
        for(int i = 0; i < gamePlayers.length; i++){
        	table.add(getScoreLabels().get(i)).width(100).top().left().spaceLeft(10);
	    } 
        table.row();     
        //adding dice button to GUI
        table.add(diceButton).width(100).height(50).spaceTop(10);
        
        //adding highscore list
        table.row();
        table.add(new Label("Highest Scores", skin)).spaceTop(20);
        table.row();
        table.add(new Label("Username", skin)).width(100).top().left();
        table.add(new Label("Scores", skin)).width(100).top().left();
        table.row();
         
        List<Highscore> topPlayers = hsc.getGameTopPlayers(highestScoreNum, true, "Number");
        
        for (int i=0; i<this.highestScoreNum;i++){
        	//table.add(new Label("Player"+i, skin)).width(100).top().left();
            //table.add(new Label("Scores"+i, skin)).width(100).top().left();
        	table.add(new Label(topPlayers.get(i).playerName, skin)).width(100).top().left();
        	table.add(new Label(topPlayers.get(i).score+"", skin)).width(100).top().left();
            table.row();
        }
	}
	
	public HashMap<String,RuleMapping> getRuleMapping() {
		return ruleMapping;
	}

	public void setRuleMapping(HashMap<String,RuleMapping> ruleMapping) {
		this.ruleMapping = ruleMapping;
	}
	
	private void dumpingScores(){
		HighscoreClient player1 = new HighscoreClient("Dylan", "snakeLadder", networkClient);
		HighscoreClient player2 = new HighscoreClient("Matt", "snakeLadder", networkClient);
		HighscoreClient player3 = new HighscoreClient("Bejo5", "snakeLadder", networkClient);
		HighscoreClient player4 = new HighscoreClient("Bejo4", "snakeLadder", networkClient);
		HighscoreClient player5 = new HighscoreClient("Bejo3", "snakeLadder", networkClient);
		
		player1.storeScore("Number", 129);
		player2.storeScore("Number", 12993);
		player3.storeScore("Number", 10193);
		player4.storeScore("Number", 193);
		player5.storeScore("Number", 1093);
		
	}

}

