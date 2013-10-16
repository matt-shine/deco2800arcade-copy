package deco2800.cyra.game;

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
import deco2800.cyra.world.Sounds;

public class MainMenu extends AbstractScreen{
	Stage stage;
	BitmapFont blackFont;
	TextureAtlas atlas;
	Skin skin;
	//SpriteBatch batch;
	TextButton button;
	TextButton button2;
	Label label;
	private int framecount = 0;
	private int framecountmax = 80;
	private int buttonframe = 0;
	private boolean keydown = false;
	
	float[] difficulty = new float[3];
	int difficultyIndex = 1;
	
	public MainMenu(Cyra game) {
		super(game);
		
		difficulty[0] = 0.21f;
		difficulty[1] = 0.76f;
		difficulty[2] = 0.91f;
	}
	
	@Override
	public void show() {		
		atlas = new TextureAtlas("data/buttons.txt");
		skin = new Skin();
		skin.addRegions(atlas);
		//blackFont = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		blackFont = new BitmapFont(Gdx.files.internal("data/font/fredericka_the_great/fredericka_the_great.fnt"), false);
		Sounds.load();
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(delta);
		
		batch.begin();
		stage.draw();
		//blackFont.draw(batch, "Test Game Of DOOOOOOOOOOOOOOOOOOOM", 50, 50);
		batch.end();
		
		if (framecount++ == framecountmax) {
			Sounds.playtest();
			framecount = 0;
			if (framecountmax > 25) {
				framecountmax -= 9;
			} else framecountmax--;
		}
		if (++buttonframe == 9) {
			buttonframe = 0;
		}
		
		if(((buttonframe % 3) == 0)) {
			button.setVisible(false);
		} else if(((buttonframe % 3) != 0)) {
			button.setVisible(true);
		}
		
		if(keydown) {
			button.setVisible(true);
		}
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
		
		LabelStyle ls = new LabelStyle(blackFont, Color.WHITE);
		label = new Label("CYRA", ls);
		label.setX(85);
		label.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 115);
		label.setWidth(width);
		label.setAlignment(Align.center);
		
		Image cyra = new Image(new Texture("data/cyra.png"));
		cyra.setX(Gdx.graphics.getWidth()/2 - cyra.getWidth()/2 - 90);
		cyra.setY(Gdx.graphics.getHeight()/2 - cyra.getHeight()/2 + 115);
		
		Image bg = new Image(new Texture("data/main_bg.png"));
		bg.setX(Gdx.graphics.getWidth()/2 - bg.getWidth()*0.75f/2);
		bg.setY(Gdx.graphics.getHeight() - bg.getHeight()*0.75f + 30);
		bg.setScale(0.75f);
		
		stage.addActor(bg);
		stage.addActor(button);
		stage.addActor(button2);
		stage.addActor(label);
		
		stage.addActor(cyra);
	}
	
	

}
