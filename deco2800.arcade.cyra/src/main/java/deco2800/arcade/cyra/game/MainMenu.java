package deco2800.arcade.cyra.game;

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
import deco2800.arcade.cyra.world.Sounds;

public class MainMenu extends AbstractScreen{
	Texture cyra;
	Stage stage;
	BitmapFont blackFont;
	TextureAtlas atlas;
	Skin skin;
	//SpriteBatch batch;
	TextButton button;
	Label label;
	private int framecount = 0;
	private int framecountmax = 80;
	private int buttonframe = 0;
	private boolean keydown = false;
	
	public MainMenu(Cyra game) {
		super(game);
		
	}
	
	@Override
	public void show() {
		cyra = new Texture("data/cyra.png");
		
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
		batch.draw(cyra, 485, Gdx.graphics.getHeight()/2-cyra.getHeight()/2 + 20);
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
		if(keydown && buttonframe%3==0) {
			TextButtonStyle style = new TextButtonStyle();
			style.up = skin.getDrawable("buttonopen");
			style.down = skin.getDrawable("buttonclose" + buttonframe/3);
			style.font = blackFont;
			button.setStyle(style);
			
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
		button = new TextButton("Click here to start!", style);
		button.setWidth(400);
		button.setHeight(100);
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
				game.setScreen(new GameScreen(game));
			}
		});
		
		LabelStyle ls = new LabelStyle(blackFont, Color.WHITE);
		label = new Label("CYRA", ls);
		label.setX(85);
		label.setY(Gdx.graphics.getHeight()/2 - label.getHeight()/2 + 20);
		label.setWidth(width);
		label.setAlignment(Align.center);
		
		stage.addActor(button);
		stage.addActor(label);
		
	}
	
	

}
