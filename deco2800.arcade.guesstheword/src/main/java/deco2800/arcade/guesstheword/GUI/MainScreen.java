package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.highscores.Highscore;
/**
 * Main screen for Guess The Word
 * 
 * @author Xu Duangui
 * */
public class MainScreen implements Screen {
	//--------------------------
	//PRIVATE VARIABLES
	//--------------------------
	/**
	 * GuessTheWord instance
	 * */
	private final GuessTheWord game;
	/**
	 * Skin instance
	 * */
	private final Skin skin;
	/**
	 * Stage instance
	 * */
	private Stage stage;
	/**
	 *Background Texture
	 * */
	private Texture texture;
	
	/**
	 * SpriteBatch instance
	 * */
	private SpriteBatch batch;
	
	/**
	 * Title Label for the main screen
	 * */
	private Label titleLabel;
	/**
	 * Start Button for the main screen
	 * */
	private TextButton startButton;
	/**
	 * Settings Button for the main screen
	 * */
	private TextButton settingsButton;
	/**
	 * Hall of fame Button for the main screen
	 * */
	private TextButton HoFButton;
	/**
	 * ScrollPane for the main screen
	 * */
	private ScrollPane scrollpane;
	
	// setup the dimensions of the menu buttons
	/**
	 * Button Width
	 * */
    private static final float BUTTON_WIDTH = 300f;
	/**
	 * Button Height
	 * */
    private static final float BUTTON_HEIGHT = 60f;
    
    
	/**
	 * MainScreen Constructor
	 * 
	 * @param game GuessTheWord Instance
	 * */
	public MainScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
	}
	
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
		
		
		stage.act(arg0);
		stage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("GuessTheWordMainScreen.png"));
		
		BitmapFont font =  new BitmapFont(Gdx.files.internal("whitefont.fnt"), false);
		LabelStyle ls = new LabelStyle();
		ls.font = font;
		
		
		
		titleLabel = new Label("Welcome to Guess The Word!" , ls);

		game.getterSetter.setAnswerCount(0);
		startButton = new TextButton("Click to Play" , skin);
		startButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//Users will start the game in a default mode
				game.getterSetter.setLevel("Default");
				System.out.println("Changing to Game Screen");
				game.setScreen(new GameScreen(game));
			}
		});
		settingsButton = new TextButton("Game Settings" , skin);
		settingsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Changing to Settings Screen");
				game.setScreen(game.settingsScreen);
			}
		});
		
		HoFButton = new TextButton("Hall of Fame " , skin);
		HoFButton.setDisabled(true);
		
		HoFButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
//				game.playerScore.getHighScore();
//				AchievementClient achClient = game.getAchievementClient();
//				AsyncFuture<AchievementProgress> playerProgress = achClient.getProgressForPlayer(game.getPlayer());
//				AsyncFuture<ArrayList<Achievement>> achievements = achClient.getAchievementsForGame(game.getGame());
//
//				for(Achievement ach  : achievements.get())
//					System.out.println(ach);
				
//				List scoreList =  new List(game.playerScore.getHighScore().toArray(), skin);
//				System.out.println(scoreList.getItems());

//				List highscore = new List(getHighScore().toArray() , skin);
//				
////				achieveText.setMessageText( sb.toString());
//				scrollpane = new ScrollPane(highscore, skin);
//				scrollpane.setVisible(true);
			}
		});		
		List highscore = new List(getHighScore().toArray() , skin);	
		scrollpane = new ScrollPane(highscore, skin);
		scrollpane.setVisible(true);

		// Creating of the table to store the buttons and labels
		Table mainTable =  new Table();
		mainTable.setFillParent(true);

		mainTable.add(titleLabel).padBottom(15);
		mainTable.row();
		mainTable.add(startButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(settingsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(HoFButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(scrollpane).width(BUTTON_WIDTH).height(BUTTON_HEIGHT + 100F).padBottom(5);
		stage.addActor(mainTable);
		
	}
	
	//--------------------------
	//PRIVATE METHOD
	//--------------------------
	/**
	 * Get the highScore and set them to readable string inputs 
	 * 
	 * @return an arraylist of strings
	 * */
	private ArrayList<String> getHighScore(){
		ArrayList<Highscore> list1 =  game.playerScore.getHighScore1();
		ArrayList<Highscore> list2 =  game.playerScore.getHighScore2();
		ArrayList<Highscore> list3 =  game.playerScore.getHighScore3();
		
		ArrayList<String> highScoreGame = new ArrayList<String>(); 
		
		
		if(list1.size()==0 ){
			String noScore = "No High Score Yet!!!" ;
			highScoreGame.add(noScore);
			return highScoreGame;
		}
		else{
			String level1Score = "Level 1 High Score: " ;
			String level1ScoreA = list1.get(0).score + " by " + list1.get(0).playerName + " on " + list1.get(0).date ;
			
			String level2Score = "Level 2 High Score: " ;
			String level2ScoreA = list2.get(0).score + " by " + list2.get(0).playerName + " on " + list2.get(0).date ;
			
			String level3Score = "Level 3 High Score: " ;
			String level3ScoreA = list3.get(0).score + " by " + list3.get(0).playerName + " on " + list3.get(0).date ;
			
			highScoreGame.add(level1Score);
			highScoreGame.add(level1ScoreA);
			highScoreGame.add(level2Score);
			highScoreGame.add(level2ScoreA);
			highScoreGame.add(level3Score);
			highScoreGame.add(level3ScoreA);
			
			return highScoreGame;
		}
		
	}
	

}
