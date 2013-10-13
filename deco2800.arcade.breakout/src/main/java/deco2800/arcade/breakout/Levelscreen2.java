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


 
public class Levelscreen2 implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	 TextureRegionDrawable level6up;
	   TextureRegionDrawable level6down;
	   TextureRegionDrawable level7up;
	   TextureRegionDrawable level7down;
	   TextureRegionDrawable level8up;
	   TextureRegionDrawable level8down;
	   TextureRegionDrawable level9up;
	   TextureRegionDrawable level9down;
	   TextureRegionDrawable level10up;
	   TextureRegionDrawable level10down;
	   TextureRegionDrawable previousup;
	   TextureRegionDrawable previousdown;
	   
	   TextureRegion level6buttonUp;
	   TextureRegion level6buttonDown;
	   TextureRegion level7buttonUp;
	   TextureRegion level7buttonDown;
	   TextureRegion level8buttonUp;
	   TextureRegion level8buttonDown;
	   TextureRegion level9buttonUp;
	   TextureRegion level9buttonDown;
	   TextureRegion level10buttonUp;
	   TextureRegion level10buttonDown;
	   TextureRegion previousbuttonUp;
	   TextureRegion previousbuttonDown;
	   
	   
	   Texture tex;
	   ImageButton level6button;
	   ImageButton level7button;
	   ImageButton level8button;
	   ImageButton level9button;
	   ImageButton level10button;
	   ImageButton previousbutton;
	   
	  
	  
	Levelscreen2(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/Level_Select2.png"));
		tex = new Texture(Gdx.files.classpath("imgs/buttons2.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 350, 210);
		
	    //level6
		level6buttonUp=tmp[0][0];
		level6buttonDown=tmp[0][1];
		level6up = new TextureRegionDrawable(level6buttonUp);
		level6down = new TextureRegionDrawable(level6buttonDown);
		level6button = new ImageButton(level6up, level6down);
		level6button.setPosition(50, 350);
	    
		level6button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //level7
		level7buttonUp=tmp[0][2];
		level7buttonDown=tmp[0][3];
		level7up = new TextureRegionDrawable(level7buttonUp);
		level7down = new TextureRegionDrawable(level7buttonDown);
		level7button = new ImageButton(level7up, level7down);
		level7button.setPosition(400, 350);
	    
		level7button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//sgame.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //level8
		level8buttonUp=tmp[1][0];
		level8buttonDown=tmp[1][1];
		level8up = new TextureRegionDrawable(level8buttonUp);
		level8down = new TextureRegionDrawable(level8buttonDown);
		level8button = new ImageButton(level8up, level8down);
		level8button.setPosition(750, 360);
	    
		level8button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.modelscreen); 
	        		
	        	}}
	    	   );
		 //level9
		level9buttonUp=tmp[1][2];
		level9buttonDown=tmp[1][3];
		level9up = new TextureRegionDrawable(level9buttonUp);
		level9down = new TextureRegionDrawable(level9buttonDown);
		level9button = new ImageButton(level9up, level9down);
		level9button.setPosition(50, 100);
	    
		level9button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //level10
		level10buttonUp=tmp[2][0];
		level10buttonDown=tmp[2][1];
		level10up = new TextureRegionDrawable(level10buttonUp);
		level10down = new TextureRegionDrawable(level10buttonDown);
		level10button = new ImageButton(level10up, level10down);
		level10button.setPosition(400, 100);
	    
		level10button.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		//game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
		 //previous
		previousbuttonUp=tmp[2][2];
		previousbuttonDown=tmp[2][3];
		previousup = new TextureRegionDrawable(previousbuttonUp);
		previousdown = new TextureRegionDrawable(previousbuttonDown);
		previousbutton = new ImageButton(previousup, previousdown);
		previousbutton.setPosition(750, 100);
	    
		previousbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        	game.setScreen(game.LevelScreen1); 
	        		
	        	}}
	    	   );
	  
	  
	       stage = new Stage(480, 640, true);
	       
	     
	       stage.addActor(level6button);
	       stage.addActor(level7button);
	       stage.addActor(level8button);
	       stage.addActor(level9button);
	       stage.addActor(level10button);
	       stage.addActor(previousbutton);
	       
	     
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
