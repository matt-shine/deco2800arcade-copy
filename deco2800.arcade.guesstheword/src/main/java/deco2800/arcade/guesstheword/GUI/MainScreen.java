package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.guesstheword.gameplay.GameModel;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.utils.AsyncFuture;

public class MainScreen implements Screen {
	
	private final GuessTheWord game;
	private final Skin skin;
	private Stage stage;
	
	private GameModel gm;
	
	private Texture texture;
	private SpriteBatch batch;
	
	private Label titleLabel;
	private TextButton startButton;
	private TextButton settingsButton;
	private TextButton achieveButton;
	
	private TextField achieveText;
	
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    
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
		
		game.getterSetter.setAnswerCount(0);
		
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
		
		achieveButton = new TextButton("Hall of Fame " , skin);
		achieveButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
//				game.playerScore.getHighScore();
//				AchievementClient achClient = game.getAchievementClient();
//				AsyncFuture<AchievementProgress> playerProgress = achClient.getProgressForPlayer(game.getPlayer());
//				AsyncFuture<ArrayList<Achievement>> achievements = achClient.getAchievementsForGame(game.getGame());
//
//				for(Achievement ach  : achievements.get())
//					System.out.println(ach);
				
				achieveText.setMessageText("Your HighScore: " + game.playerScore.getHighScore() );
			}
		});
		
		achieveText = new TextField("", skin);
		achieveText.setMessageText("");

		
		// Creating of the table to store the buttons and labels
		Table mainTable =  new Table();
		mainTable.setFillParent(true);

		mainTable.add(titleLabel).padBottom(15);
		mainTable.row();
		mainTable.add(startButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(settingsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(achieveButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		
		mainTable.row();
		mainTable.add(achieveText).width(BUTTON_WIDTH).height(BUTTON_HEIGHT + 60F).padBottom(5);
		
		stage.addActor(mainTable);
		
	}
	

}
