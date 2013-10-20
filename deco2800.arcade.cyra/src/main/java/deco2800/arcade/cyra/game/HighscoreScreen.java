package deco2800.arcade.cyra.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import deco2800.arcade.client.highscores.Highscore;

/** This class controls the components that make up the main menu - buttons,
 * images, texts and draw them onto the GameScreen.
 *
 * @author Game Over
 */
public class HighscoreScreen extends AbstractScreen{
	Stage stage;
	BitmapFont blackFont;
	TextureAtlas atlas;
	Skin skin;
	//SpriteBatch batch;
	TextButton button;
	Label label;
	Label playerLabel;
	Label scoreLabel;
	private boolean keydown = false;
	
	private List<Highscore> highscores;
	
	public HighscoreScreen(Cyra game) {
		super(game);
		
	}
	
	@Override
	public void show() {		
		atlas = new TextureAtlas("buttons.txt");
		skin = new Skin();
		skin.addRegions(atlas);
		blackFont = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
		game.addHighscore(60044);
		game.addHighscore(2334);
		
		highscores = game.getHighscores();
		System.out.println("size of hs list " + highscores.size());
		
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
		button = new TextButton("Back", style);
		button.setHeight(90);
		button.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);
		button.setY(Gdx.graphics.getHeight()/2 - button.getHeight()/2 - 200);
		
		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				keydown = true;
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				keydown = false;
				game.setScreen(new MainMenu(game));
			}
		});
		
		
		
		LabelStyle ls = new LabelStyle(blackFont, Color.WHITE);
		label = new Label("Highscores", ls);
		//label.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);
		label.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 115);
		label.setWidth(width);
		label.setAlignment(Align.top);
		
		String playerText = "";
		String scoreText = "";
		//Create string of scores
		for (Highscore hs: highscores) {
			playerText += hs.playerName;
			playerText += "\n";
			scoreText += hs.score;
			scoreText += "\n";
		}
		
		
		
		
		
		playerLabel = new Label(playerText, ls);
		playerLabel.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 50);
		playerLabel.setX(Gdx.graphics.getWidth()/2 - 200);
		
		scoreLabel = new Label(scoreText, ls);
		scoreLabel.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 50);
		scoreLabel.setX(Gdx.graphics.getWidth()/2 + 200);
		
		
		
		
		Image cyra = new Image(new Texture(Gdx.files.internal("cyra.png")));
		cyra.setX(Gdx.graphics.getWidth()/2 - cyra.getWidth()/2);
		cyra.setY(Gdx.graphics.getHeight()/2 - cyra.getHeight()/2 + 115);
		
		Image bg = new Image(new Texture(Gdx.files.internal("main_bg.png")));
		bg.setX(Gdx.graphics.getWidth()/2 - bg.getWidth()*0.75f/2);
		bg.setY(Gdx.graphics.getHeight() - bg.getHeight()*0.75f + 30);
		bg.setScale(0.75f);
		
		//stage.addActor(bg);
		stage.addActor(button);
		stage.addActor(label);
		stage.addActor(playerLabel);
		stage.addActor(scoreLabel);
		
		//stage.addActor(cyra);
	}
	
	

}
