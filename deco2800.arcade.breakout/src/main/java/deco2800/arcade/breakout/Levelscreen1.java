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


 
public class LevelScreen1 implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	 TextureRegionDrawable level1up;
	   TextureRegionDrawable level1down;
	   TextureRegionDrawable level2up;
	   TextureRegionDrawable level2down;
	   TextureRegionDrawable level3up;
	   TextureRegionDrawable level3down;
	   TextureRegionDrawable level4up;
	   TextureRegionDrawable level4down;
	   TextureRegionDrawable level5up;
	   TextureRegionDrawable level5down;
	   TextureRegionDrawable nextup;
	   TextureRegionDrawable nextdown;
	   
	   TextureRegion level1buttonUp;
	   TextureRegion level1buttonDown;
	   TextureRegion level2buttonUp;
	   TextureRegion level2buttonDown;
	   TextureRegion level3buttonUp;
	   TextureRegion level3buttonDown;
	   TextureRegion level4buttonUp;
	   TextureRegion level4buttonDown;
	   TextureRegion level5buttonUp;
	   TextureRegion level5buttonDown;
	   TextureRegion nextbuttonUp;
	   TextureRegion nextbuttonDown;
	   
	   
	   Texture tex;
	   ImageButton level1button;
	   ImageButton level2button;
	   ImageButton level3button;
	   ImageButton level4button;
	   ImageButton level5button;
	   ImageButton nextbutton;
	   
	  
	  
	LevelScreen1(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/Level_Select1.png"));
		tex = new Texture(Gdx.files.classpath("imgs/buttons1.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 350, 210);
		
	    //level1
		level1buttonUp=tmp[0][0];
		level1buttonDown=tmp[0][1];
		level1up = new TextureRegionDrawable(level1buttonUp);
		level1down = new TextureRegionDrawable(level1buttonDown);
		level1button = new ImageButton(level1up, level1down);
		level1button.setPosition(50, 350);
	    
		level1button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.gamescreen); 
	        		
	        	}}
	    	   );
		 //level2
		level2buttonUp=tmp[0][2];
		level2buttonDown=tmp[0][3];
		level2up = new TextureRegionDrawable(level2buttonUp);
		level2down = new TextureRegionDrawable(level2buttonDown);
		level2button = new ImageButton(level2up, level2down);
		level2button.setPosition(400, 350);
	    
		level2button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//sgame.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //level3
		level3buttonUp=tmp[1][0];
		level3buttonDown=tmp[1][1];
		level3up = new TextureRegionDrawable(level3buttonUp);
		level3down = new TextureRegionDrawable(level3buttonDown);
		level3button = new ImageButton(level3up, level3down);
		level3button.setPosition(750, 350);
	    
		level3button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.modelscreen); 
	        		
	        	}}
	    	   );
		 //level4
		level4buttonUp=tmp[1][2];
		level4buttonDown=tmp[1][3];
		level4up = new TextureRegionDrawable(level4buttonUp);
		level4down = new TextureRegionDrawable(level4buttonDown);
		level4button = new ImageButton(level4up, level4down);
		level4button.setPosition(50, 90);
	    
		level4button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //level5
		level5buttonUp=tmp[2][0];
		level5buttonDown=tmp[2][1];
		level5up = new TextureRegionDrawable(level5buttonUp);
		level5down = new TextureRegionDrawable(level5buttonDown);
		level5button = new ImageButton(level5up, level5down);
		level5button.setPosition(400, 100);
	    
		level5button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        	//	game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //next
		nextbuttonUp=tmp[2][2];
		nextbuttonDown=tmp[2][3];
		nextup = new TextureRegionDrawable(nextbuttonUp);
		nextdown = new TextureRegionDrawable(nextbuttonDown);
		nextbutton = new ImageButton(nextup, nextdown);
		nextbutton.setPosition(750, 100);
	    
		nextbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        	game.setScreen(game.LevelScreen2); 
	        		
	        	}}
	    	   );
	  
	  
	       stage = new Stage(480, 640, true);
	       
	     
	       stage.addActor(level1button);
	       stage.addActor(level2button);
	       stage.addActor(level3button);
	       stage.addActor(level4button);
	       stage.addActor(level5button);
	       stage.addActor(nextbutton);
	       
	     
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
		Gdx.input.setInputProcessor(stage);
		
	}

	
}
