package deco2800.arcade.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.client.ArcadeInputMux;

public class HelpScreen implements Screen {

	// Define variables for screen
	Texture splashTexture;
	Texture splashTexture2, splashTexture3;
	Sprite splashSprite;
	private Chess game;
	private Stage stage;
	private BitmapFont BmFontA, BmFontB;
	private TextureAtlas map;
	private Skin skin;
	private SpriteBatch batch;
	private TextButton backButton;

	public HelpScreen(Chess game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		map.dispose();
		BmFontB.dispose();
		BmFontA.dispose();
		stage.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		int height = Chess.SCREENHEIGHT;

		batch.draw(splashTexture, 0, 0);
		batch.end();
		batch.begin();
		batch.draw(splashTexture2, 0, (float) ((float) height * 0.88));
		batch.end();

		batch.begin();
		batch.draw(splashTexture3, 30, 120);
		batch.end();

		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		splashTexture = new Texture(Gdx.files.internal("chessMenu.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTexture2 = new Texture(Gdx.files.internal("chessTitle.png"));
		splashTexture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTexture3 = new Texture(Gdx.files.internal("helpScreen.png"));
		splashTexture3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);
		// moves sprite to centre of screen
		splashSprite.setX(Gdx.graphics.getWidth() / 2
				- (splashSprite.getWidth() / 2));
		splashSprite.setY(Gdx.graphics.getHeight() / 2
				- (splashSprite.getHeight() / 2));
		batch = new SpriteBatch();
		map = new TextureAtlas("b.pack");
		skin = new Skin();
		skin.addRegions(map);
		BmFontA = new BitmapFont(Gdx.files.internal("imgs/GameFont2.fnt"),
				false);
		BmFontB = new BitmapFont(Gdx.files.internal("imgs/GameFont2.fnt"),
				false);

		int width = Chess.SCREENWIDTH;
		int height = Chess.SCREENHEIGHT;

		stage = new Stage(width, height, true);

		ArcadeInputMux.getInstance().addProcessor(stage);

		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = BmFontB;

		backButton = new TextButton("Back to Menu", style);
		backButton.setWidth(200);
		backButton.setHeight(50);
		backButton.setX((float) (width * 0.02));
		backButton.setY((float) (height * 0.02));

		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MenuScreen(game));
			}
		});

		stage.addActor(backButton);
	}
}