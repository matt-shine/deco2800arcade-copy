package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeInputMux;

public class ScoreScreen implements Screen {
	
	//@SuppressWarnings("unused")
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton backButton;
    private Label label;
	
	public ScoreScreen(BurningSkies game) {
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
		game.stopSong();
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

        stage.act(delta);

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
		batch = new SpriteBatch();
        atlas = new TextureAtlas("images/menu/button.pack");
        skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        
        int width = BurningSkies.SCREENWIDTH;
        int height = BurningSkies.SCREENHEIGHT;
        
        stage = new Stage(width, height, true);
	
        ArcadeInputMux.getInstance().addProcessor(stage);
	
	    TextButtonStyle style = new TextButtonStyle();
	    style.up = skin.getDrawable("buttonnormal");
	    style.down = skin.getDrawable("buttonpressed");
	    style.font = black;
	    
	    backButton = new TextButton("Back", style);
	    backButton.setWidth(200);
	    backButton.setHeight(50);
	    backButton.setX((float)(width*0.02));
	    backButton.setY((float)(height*0.02));
	    
	    backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new MenuScreen(game));
            }
	    });
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Burning Skies", ls);
	    label.setX(0);
	    label.setY((float)(height*0.95));
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	    
	    stage.addActor(backButton);
	    stage.addActor(label);	    
	}
}
