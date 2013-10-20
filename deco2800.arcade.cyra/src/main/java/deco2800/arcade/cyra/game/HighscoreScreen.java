package deco2800.arcade.cyra.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.client.highscores.Highscore;

/** This class controls the components that make up the Highscore screen - buttons
 * and labels. Gets passed highscore information from Cyra class.
 *
 * @author Game Over
 */
public class HighscoreScreen extends AbstractScreen{
	private Stage stage;
	private BitmapFont blackFont;
	private BitmapFont listFont;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton button;
	private Label titleLabel;
	private Label playerLabel;
	private Label scoreLabel;
	private String playerText;
	private String scoreText;
	
	/**
	 * Basic constructor.
	 * @param game
	 */
	public HighscoreScreen(Cyra game) {
		super(game);
		
	}
	
	@Override
	public void show() {		
		atlas = new TextureAtlas("buttons.txt");
		skin = new Skin();
		skin.addRegions(atlas);
		
		//Create fonts
		blackFont = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
		listFont = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
		listFont.setScale(0.6f);
		
		//Get highscore information
		List<Highscore> highscores = game.getHighscores();
		//Create string of scores
		playerText = "Player\n\n";
		scoreText = "Score\n\n";
		for (Highscore hs: highscores) {
			playerText += hs.playerName;
			playerText += "\n";
			scoreText += hs.score;
			scoreText += "\n";
		}
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();

	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonopen");
		style.down = skin.getDrawable("buttonclose0");
		style.font = blackFont;
		
		//Label styles for title text and list texts
		LabelStyle titleStyle = new LabelStyle(blackFont, Color.WHITE);
		LabelStyle listStyle = new LabelStyle(listFont, Color.WHITE);

		//Create elements and place them
		
		//Back button to return to Main Menu
		button = new TextButton("Back", style);
		button.setHeight(90);
		button.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);
		button.setY(Gdx.graphics.getHeight()/2 - button.getHeight()/2 - 300);
		
		//Add action for pressing the button
		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenu(game));
			}
		});
		
		//Label for the title of screen
		titleLabel = new Label("Highscores", titleStyle);
		titleLabel.setX(Gdx.graphics.getWidth()/2 - titleLabel.getWidth()/2);
		titleLabel.setY(Gdx.graphics.getHeight()/2 - titleLabel.getHeight()/2 + 300);
		
		//Label for the list of player names
		playerLabel = new Label(playerText, listStyle);
		playerLabel.setY(Gdx.graphics.getHeight()/2 - titleLabel.getHeight()/2 - 100);
		playerLabel.setX(Gdx.graphics.getWidth()/2 - 200);
		
		//Label for the list of scores
		scoreLabel = new Label(scoreText, listStyle);
		scoreLabel.setY(Gdx.graphics.getHeight()/2 - titleLabel.getHeight()/2 - 100);
		scoreLabel.setX(Gdx.graphics.getWidth()/2 + 200);
		
		//Add elements to stage
		stage.addActor(button);
		stage.addActor(titleLabel);
		stage.addActor(playerLabel);
		stage.addActor(scoreLabel);
		
	}
	
	

}
