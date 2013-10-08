package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeInputMux;

public class HelpScreen implements Screen {
	
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton backButton;
    private Label label;
    private Image background;	
	private MenuInputProcessor processor;
    
	public HelpScreen(BurningSkies game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		ArcadeInputMux.getInstance().removeProcessor(processor);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
    		game.setScreen(game.menuScreen);
    	}
        
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
		skin = new Skin(Gdx.files.internal("images/menu/uiskin32.json"));
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        background = new Image(new Texture(Gdx.files.internal("images/menu/menu_background.png")));
        
        int width = BurningSkies.SCREENWIDTH;
        int height = BurningSkies.SCREENHEIGHT;
        stage = new Stage(width, height, true);
	
        ArcadeInputMux.getInstance().addProcessor(stage);
        processor = new MenuInputProcessor(game);
    	ArcadeInputMux.getInstance().addProcessor(processor);

	    backButton = new TextButton("Back", skin);
	    backButton.setWidth(200);
	    backButton.setHeight(50);
	    backButton.setX((float)(width*0.02));
	    backButton.setY((float)(height*0.02));
	    
	    backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(game.menuScreen);
            }
	    });
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Help", ls);
	    label.setX(0);
	    label.setY((float)(height*0.95));
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	    
	    stage.addActor(backButton);
	    stage.addActor(label);
	    stage.addActor(background);
	    background.toBack();
	}
}