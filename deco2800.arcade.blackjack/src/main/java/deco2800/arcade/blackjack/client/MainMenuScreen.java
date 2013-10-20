package deco2800.arcade.blackjack.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.Game;

public class MainMenuScreen implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture textureBackground;
	private Sprite background;
	public Blackjack myGame;
	private Stage stage;
	JoinTableScreen JoinTableScreen;

	public MainMenuScreen(
	        Blackjack myGame )
	    {
		this.myGame = myGame;
	    }
	@Override
    public void show()
    {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Texture.setEnforcePotImages(false);
		
		camera = new OrthographicCamera();
	    camera.viewportHeight = h;
	    camera.viewportWidth = w;

	    camera.position.set(camera.viewportWidth * .5f,
	    camera.viewportHeight * .5f, 0f);
	    camera.update();
		
		batch = new SpriteBatch();
 
        // load the image and create the texture region
        textureBackground = new Texture(Gdx.files.internal("data/menu1.png"));
 
        // we set the linear texture filter to improve the stretching
        textureBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
 
        background = new Sprite(textureBackground);
		background.setSize(w, h);
		
		//buttons
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        Table table2 = new Table();
        stage.addActor(table);
        table.setSize(187, 117);
		table.setPosition(20, 20);
		stage.addActor(table2);
		table2.setSize(183, 138);
		table2.setPosition(550, 275);
        
        Texture textureButton1 = new Texture(Gdx.files.internal("data/exitbutton.png"));
        Texture textureButton2 = new Texture(Gdx.files.internal("data/blackjackbutton.png"));
        TextureRegion imageExit = new TextureRegion(textureButton1);
        TextureRegion imageBlackjack = new TextureRegion(textureButton2);
        
        Drawable imageUp = new TextureRegionDrawable(imageExit);
		Drawable imageUp2 = new TextureRegionDrawable(imageBlackjack);

        ImageButton exitbutton1 = new ImageButton(new ImageButton.ImageButtonStyle(imageUp, imageUp, imageUp, imageUp, imageUp, imageUp));
        ImageButton imagebuttonblackjack = new ImageButton(new ImageButton.ImageButtonStyle(imageUp2, imageUp2, imageUp2, imageUp2, imageUp2, imageUp2));
        exitbutton1.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				System.exit(0);
				return false;
			}
		});
        imagebuttonblackjack.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				JoinTableScreen = new JoinTableScreen(myGame);
				myGame.setScreen(JoinTableScreen);
				return false;
			}
		});
        table.add(exitbutton1);
        table2.add(imagebuttonblackjack);
    }
 
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		batch.end();
		stage.draw();
    }
 
    @Override
    public void dispose()
    {
		batch.dispose();
		stage.dispose();
		textureBackground.dispose();
    }
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
