package deco2800.arcade.breakout.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.breakout.Breakout;
import deco2800.arcade.client.ArcadeInputMux;

/**
 * Class for the SplashScreen 
 * @author Tony Wu and ZhuLun Liang
 * 
 */
 
public class HelpScreen2 implements Screen  {
	/*
	 * creates instance variables for each image.
	 */
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	private Stage stage;
	private TextureRegionDrawable backup;
	private TextureRegionDrawable backdown;
	private TextureRegion backbuttonUp;
	private TextureRegion backbuttonDown;
	private Texture tex;
	private ImageButton backbutton;
	  
	/**
	 * Constructor.
	 * Load the image from the resource
	 * @param game -The Breakout game
	 */    
	public HelpScreen2(final Breakout game) {
		
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/HelpScreen2.png"));
		tex = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 130, 45);
	    //Back button
	    backbuttonUp=tmp[2][2];
	    backbuttonDown=tmp[2][3];
	    backup = new TextureRegionDrawable(backbuttonUp);
	    backdown = new TextureRegionDrawable(backbuttonDown);
	    backbutton = new ImageButton(backup, backdown);
	    backbutton.setPosition(0, 590);
	    backbutton.addListener(new InputListener(){
	    	public boolean touchDown(InputEvent event, float x, float y, 
	    		int pointer, int button) { 
	    			//touch down method is needed for the rest to work
	        		return true;
	        	}
	        public void touchUp(InputEvent event, float x, float y, 
	        	int pointer, int button) { //on button release do this
	        		game.setScreen(game.getMenuScreen()); 
	        }}
	    );
	    stage = new Stage(480, 640, true);
	    stage.addActor(backbutton);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}
	/**
	 * render for the screen
	 */
	@Override
	public void render(float arg0) {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
		stage.act();
		stage.draw();	       
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		//Gdx.input.setInputProcessor(stage);
        ArcadeInputMux.getInstance().addProcessor(stage);
	}
}