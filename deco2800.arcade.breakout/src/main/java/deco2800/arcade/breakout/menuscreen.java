package deco2800.arcade.breakout;
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

import deco2800.arcade.client.ArcadeSystem;


 
public class menuscreen implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	   TextureRegionDrawable gameup;
	   TextureRegionDrawable gamedown;
	   TextureRegionDrawable levelup;
	   TextureRegionDrawable leveldown;
	   TextureRegionDrawable achievementup;
	   TextureRegionDrawable achievementdown;
	   TextureRegionDrawable quitup;
	   TextureRegionDrawable quitdown;
	   
	   TextureRegion newgamebuttonUp;
	   TextureRegion newgamebuttonDown;
	   TextureRegion levelbuttonUp;
	   TextureRegion levelbuttonDown;
	   TextureRegion rankingbuttonUp;
	   TextureRegion rankingbuttonDown;
	   TextureRegion achievementbuttonUp;
	   TextureRegion achievementbuttonDown;
	   TextureRegion quitbuttonUp;
	   TextureRegion quitbuttonDown;
	   
	   
	   Texture tex;
	   ImageButton gamebutton;
	   ImageButton levelbutton;
	   ImageButton rankingbutton;
	   ImageButton achievementbutton;
	   ImageButton quitbutton;
	   
	   
	
	menuscreen(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/menu.png"));
		tex = new Texture(Gdx.files.classpath("imgs/show_mop.png"));
		TextureRegion[][] tmp1 = TextureRegion.split(tex, 120, 120);
		TextureRegion[][] tmp2 = TextureRegion.split(tex, 120, 120);
		
	    newgamebuttonUp = tmp1[0][0];
	    newgamebuttonDown = tmp1[0][1];
	    gameup = new TextureRegionDrawable(newgamebuttonUp);
	    gamedown = new TextureRegionDrawable(newgamebuttonDown);
	    gamebutton = new ImageButton(gameup, gamedown);
	    gamebutton.setPosition(450, 400);
	    gamebutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		
	        		game.setScreen(game.gamescreen); 
	        	}}
	    	   );
	    //quit
	    quitbuttonUp=tmp2[1][0];
	    quitbuttonDown=tmp2[1][1];
	    quitup = new TextureRegionDrawable(quitbuttonUp);
	    quitdown = new TextureRegionDrawable(quitbuttonDown);
	    quitbutton = new ImageButton(quitup, quitdown);
	    quitbutton.setPosition(450, 300);
	    quitbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		
	        		ArcadeSystem.goToGame(ArcadeSystem.UI);
	        	}}
	    	   );

	       stage = new Stage(560, 640, true);
	       gamebutton.setWidth(200f);
	       gamebutton.setHeight(50f);
	       quitbutton.setWidth(200f);
	       quitbutton.setHeight(50f);
	       
	       
	       Gdx.input.setInputProcessor(stage);
	       stage.addActor(gamebutton);
	       stage.addActor(quitbutton);
	       
		
	}
	
	
	  
	
	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
		
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
	public void render(float arg0) {
		
		//	game.setScreen(game.gamescreen);
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
}
