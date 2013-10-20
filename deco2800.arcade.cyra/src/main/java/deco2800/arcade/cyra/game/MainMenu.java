package deco2800.arcade.cyra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import deco2800.arcade.cyra.world.Sounds;

/** This class controls the components that make up the main menu - buttons,
 * images, texts and draw them onto the GameScreen.
 *
 * @author Game Over
 */
public class MainMenu extends AbstractScreen{
	private Stage stage;
	private BitmapFont blackFont;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton button;
	private TextButton button2;
	private TextButton button3;
	private Label label;
	
	private float[] difficulty = new float[3];
	private int difficultyIndex = 1;
	
	public MainMenu(Cyra game) {
		super(game);
		
		//Set values for difficulty
		difficulty[0] = 0.21f;
		difficulty[1] = 0.76f;
		difficulty[2] = 0.91f;
		Sounds.setSoundEnabled(true);
		Sounds.loadAll();
		Sounds.playMenuMusic();
		
	}
	
	@Override
	public void show() {		
		atlas = new TextureAtlas("buttons.txt");
		skin = new Skin();
		skin.addRegions(atlas);
		blackFont = new BitmapFont(Gdx.files.internal("font/fredericka_the_great/fredericka_the_great.fnt"), false);
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
		
		button = new TextButton("START!", style);
		button.setHeight(90);
		button.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);
		button.setY(Gdx.graphics.getHeight()/2 - button.getHeight()/2 - 130);
		
		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game, difficulty[difficultyIndex]));
			}
		});
		
		button2 = new TextButton("Difficulty: Medium", style);
		button2.setX(Gdx.graphics.getWidth()/2 - button2.getWidth()/2);
		button2.setY(Gdx.graphics.getHeight()/2 - button.getHeight()/2 - 270);
		button2.setHeight(90);
		button2.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				switch(difficultyIndex) {
					case 0:
						difficultyIndex++;
						button2.setText("Difficulty: Medium");
						break;
					case 1:
						difficultyIndex++;
						button2.setText("Difficulty: Hard");
						break;
					case 2:
						difficultyIndex = 0;
						button2.setText("Difficulty: Easy");
						break;
					default:
						break;
				}
			}
		});
		
		button3 = new TextButton("Highscores", style);
		button3.setHeight(90);
		button3.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);
		button3.setY(Gdx.graphics.getHeight()/2 - button.getHeight()/2 - 200);
		
		button3.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new HighscoreScreen(game));
			}
		});
		
		LabelStyle ls = new LabelStyle(blackFont, Color.WHITE);
		label = new Label("CYRA", ls);
		label.setX(85);
		label.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 115);
		label.setWidth(width);
		label.setAlignment(Align.center);
		
		Image cyra = new Image(new Texture(Gdx.files.internal("cyra.png")));
		cyra.setX(Gdx.graphics.getWidth()/2 - cyra.getWidth()/2 - 90);
		cyra.setY(Gdx.graphics.getHeight()/2 - cyra.getHeight()/2 + 115);
		
		Image bg = new Image(new Texture(Gdx.files.internal("main_bg.png")));
		bg.setX(Gdx.graphics.getWidth()/2 - bg.getWidth()*0.75f/2);
		bg.setY(Gdx.graphics.getHeight() - bg.getHeight()*0.75f + 30);
		bg.setScale(0.75f);
		
		stage.addActor(bg);
		stage.addActor(button);
		stage.addActor(button2);
		stage.addActor(button3);
		stage.addActor(label);
		
		stage.addActor(cyra);
	}
	
	

}
