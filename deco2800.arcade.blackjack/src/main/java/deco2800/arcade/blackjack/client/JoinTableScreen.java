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

public class JoinTableScreen implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture textureBackground;
	private Sprite background;
	public Blackjack myGame;
	private Stage stage;
	JoinTable2Screen JoinTable2Screen;
	MainMenuScreen MainMenuScreen;
	HowToScreen HowToScreen;

	public JoinTableScreen(
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
        Table table3 = new Table();
        stage.addActor(table);
        table.setSize(187, 117);
		table.setPosition(20, 20);
		stage.addActor(table2);
		table2.setSize(183, 138);
		table2.setPosition(550, 275);
		stage.addActor(table3);
		table3.setSize(183, 138);
		table3.setPosition(550, 100);
        
        Texture textureButton1 = new Texture(Gdx.files.internal("data/backbutton.png"));
        TextureRegion imageBack = new TextureRegion(textureButton1);
        Texture textureButton2 = new Texture(Gdx.files.internal("data/tables.png"));
        TextureRegion imageTables = new TextureRegion(textureButton2);
        Texture textureButton3 = new Texture(Gdx.files.internal("data/howto.png"));
        TextureRegion imageHowTo = new TextureRegion(textureButton3);
        
        Drawable imageUp = new TextureRegionDrawable(imageBack);
        Drawable imageUp2 = new TextureRegionDrawable(imageTables);
        Drawable imageUp3 = new TextureRegionDrawable(imageHowTo);


        ImageButton backbutton = new ImageButton(new ImageButton.ImageButtonStyle(imageUp, imageUp, imageUp, imageUp, imageUp, imageUp));
        ImageButton tablesbutton = new ImageButton(new ImageButton.ImageButtonStyle(imageUp2, imageUp2, imageUp2, imageUp2, imageUp2, imageUp2));
        ImageButton howtobutton = new ImageButton(new ImageButton.ImageButtonStyle(imageUp3, imageUp3, imageUp3, imageUp3, imageUp3, imageUp3));
        backbutton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        backbutton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				MainMenuScreen = new MainMenuScreen(myGame);
				myGame.setScreen(MainMenuScreen);
				return false;
			}
		});
        tablesbutton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				JoinTable2Screen = new JoinTable2Screen(myGame);
				myGame.setScreen(JoinTable2Screen);
				return false;
			}
		});
        howtobutton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				HowToScreen = new HowToScreen(myGame);
				myGame.setScreen(HowToScreen);
				return false;
			}
		});
        table.add(backbutton);
        table2.add(tablesbutton);
        table3.add(howtobutton);
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
