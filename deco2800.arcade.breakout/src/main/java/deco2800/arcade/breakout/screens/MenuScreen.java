package deco2800.arcade.breakout.screens;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.breakout.Breakout;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class MenuScreen implements Screen {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	/*
	 * creates instance variables for each image buttons.
	 */
	TextureRegionDrawable gameup;
	TextureRegionDrawable gamedown;
	TextureRegionDrawable levelup;
	TextureRegionDrawable leveldown;
	TextureRegionDrawable rankingup;
	TextureRegionDrawable rankingdown;
	TextureRegionDrawable helpup;
	TextureRegionDrawable helpdown;
	TextureRegionDrawable quitup;
	TextureRegionDrawable quitdown;
	TextureRegionDrawable modelup;
	TextureRegionDrawable modeldown;

	TextureRegion newgamebuttonUp;
	TextureRegion newgamebuttonDown;
	TextureRegion levelbuttonUp;
	TextureRegion levelbuttonDown;
	TextureRegion rankingbuttonUp;
	TextureRegion rankingbuttonDown;
	TextureRegion helpbuttonUp;
	TextureRegion helpbuttonDown;
	TextureRegion quitbuttonUp;
	TextureRegion quitbuttonDown;
	TextureRegion modelbuttonUp;
	TextureRegion modelbuttonDown;

	Texture tex;
	ImageButton gamebutton;
	ImageButton levelbutton;
	ImageButton rankingbutton;
	ImageButton helpbutton;
	ImageButton quitbutton;
	ImageButton modelbutton;

	/**
	 * Instantiate a new instance of the MenuScreen class
	 * 
	 * @param game
	 *            - The current Breakout class
	 */
	public MenuScreen(final Breakout game) {

		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/final_background.png"));
		/*
		 * insert the button images and split to get each image button
		 */
		tex = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 130, 45);

		/*
		 * set the new game button
		 */
		newgamebuttonUp = tmp[0][0];
		newgamebuttonDown = tmp[0][1];
		gameup = new TextureRegionDrawable(newgamebuttonUp);
		gamedown = new TextureRegionDrawable(newgamebuttonDown);
		gamebutton = new ImageButton(gameup, gamedown);
		gamebutton.setPosition(485, 350);
		// Instantiate a input listener
		gamebutton.addListener(new InputListener() {
			/**
			 * called when a button goes down on the actor, if return true, it
			 * will receive all touch up events.
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 * @return - true
			 */
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				return true;
			}

			/**
			 * called when a button goes up, the screen will move to the model
			 * screen
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 */

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.getModelscreen());
			}
		});

		/*
		 * set the new help button
		 */
		helpbuttonUp = tmp[1][2];
		helpbuttonDown = tmp[1][3];
		helpup = new TextureRegionDrawable(helpbuttonUp);
		helpdown = new TextureRegionDrawable(helpbuttonDown);
		helpbutton = new ImageButton(helpup, helpdown);
		helpbutton.setPosition(485, 250);
		// Instantiate a input listener
		helpbutton.addListener(new InputListener() {
			/**
			 * called when a button goes down on the actor, if return true, it
			 * will receive all touch up events.
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 * @return - true
			 */
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { // touch down method is needed
												// for the rest to work
				return true;
			}

			/**
			 * called when a button goes up, the screen will move to the help
			 * screen
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 */
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { // on button release do this
				game.setScreen(game.getHelpscreen1());
			}
		});
		/*
		 * set the new quit button
		 */
		quitbuttonUp = tmp[2][0];
		quitbuttonDown = tmp[2][1];
		quitup = new TextureRegionDrawable(quitbuttonUp);
		quitdown = new TextureRegionDrawable(quitbuttonDown);
		quitbutton = new ImageButton(quitup, quitdown);
		quitbutton.setPosition(485, 150);
		// Instantiate a input listener
		quitbutton.addListener(new InputListener() {
			/**
			 * called when a button goes down on the actor, if return true, it
			 * will receive all touch up events.
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 * @return - true
			 */
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { // touch down method is needed
												// for the rest to work

				return true;
			}

			/**
			 * called when a button goes up, the screen will move to the
			 * arcadesystem.ui screen
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 */
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { // on button release do this
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
		});

		stage = new Stage(480, 640, true);

		/*
		 * add all buttons to the stage
		 */

		stage.addActor(gamebutton);
		stage.addActor(quitbutton);
		stage.addActor(helpbutton);


		
		//new game
	    newgamebuttonUp = tmp[0][0];
	    newgamebuttonDown = tmp[0][1];
	    gameup = new TextureRegionDrawable(newgamebuttonUp);
	    gamedown = new TextureRegionDrawable(newgamebuttonDown);
	    gamebutton = new ImageButton(gameup, gamedown);
	    gamebutton.setPosition(485, 350);
	    gamebutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.getModelscreen());
	        	}}
	    	   );
	  
	 /*
	    //ranking
	    rankingbuttonUp=tmp[1][0];
	    rankingbuttonDown=tmp[1][1];
	    rankingup = new TextureRegionDrawable(rankingbuttonUp);
	    rankingdown = new TextureRegionDrawable(rankingbuttonDown);
	    rankingbutton = new ImageButton(rankingup, rankingdown);
	    rankingbutton.setPosition(480, 270);
	    rankingbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.RankingScreen); 
	        		
	        	}}
	    	   );
	    	   */
	    
	    //help
	    helpbuttonUp=tmp[1][2];
	    helpbuttonDown=tmp[1][3];
	    helpup = new TextureRegionDrawable(helpbuttonUp);
	    helpdown = new TextureRegionDrawable(helpbuttonDown);
	    helpbutton = new ImageButton(helpup, helpdown);
	    helpbutton.setPosition(485, 250);
	    helpbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.getHelpscreen1()); 
	        	}}
	    	   );
	    //quit
	    quitbuttonUp=tmp[2][0];
	    quitbuttonDown=tmp[2][1];
	    quitup = new TextureRegionDrawable(quitbuttonUp);
	    quitdown = new TextureRegionDrawable(quitbuttonDown);
	    quitbutton = new ImageButton(quitup, quitdown);
	    quitbutton.setPosition(485, 150);
	    quitbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		ArcadeSystem.goToGame(ArcadeSystem.UI);
	        	}}
	    	   );
	    
	       stage = new Stage(480, 640, true);
	       
	       stage.addActor(gamebutton);
	       stage.addActor(quitbutton);     
	       stage.addActor(helpbutton);
		

	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();

	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);		
	}

	@Override
	public void pause() {
	}

	@Override
	/**
	 * draw back ground and start stage
	 */
	public void render(float arg0) {
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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
		//ArcadeInputMux.getInstance().addProcessor(stage);		
		Gdx.input.setInputProcessor(stage);
	}

}
