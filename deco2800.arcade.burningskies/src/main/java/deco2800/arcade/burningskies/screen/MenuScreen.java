package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import deco2800.arcade.burningskies.BurningSkies;

public class MenuScreen implements Screen {
	
	//@SuppressWarnings("unused")
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private TextureAtlas atlas;
    private  Skin skin;
    private SpriteBatch batch;
    private TextButton button;
    private Label label;
	
	public MenuScreen( BurningSkies game){
		this.game = game;		
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        atlas.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();

	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
            stage = new Stage(width, height, true);
	    stage.clear();
	
	    Gdx.input.setInputProcessor(stage);
	
	    TextButtonStyle style = new TextButtonStyle();
	    style.up = skin.getDrawable("buttonnormal");
	    style.down = skin.getDrawable("buttonpressed");
	    style.font = black;
	
	    button = new TextButton("Start", style);
	    button.setWidth(400);
	    button.setHeight(100);
	    button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);
	    button.setY(Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
	
	    button.addListener(new InputListener() {
	            public boolean touchDown(InputEvent event, float x, float y,
	                            int pointer, int button) {
	                    return true;
	            }
	
	            public void touchUp(InputEvent event, float x, float y,
	                            int pointer, int button) {
	                    game.setScreen(new PlayScreen(game));
	            }
	    });
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Burning Skies", ls);
	    label.setX(0);
	    label.setY(Gdx.graphics.getHeight() / 2 + 100);
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	
	    stage.addActor(button);
	    stage.addActor(label);

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
        atlas = new TextureAtlas("images/button.pack");
        skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("images/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/font.fnt"), false);

	}

}
