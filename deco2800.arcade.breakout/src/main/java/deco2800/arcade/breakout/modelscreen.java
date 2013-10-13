package deco2800.arcade.breakout;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
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


 
public class Modelscreen implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	   TextureRegionDrawable backup;
	   TextureRegionDrawable backdown;
	   TextureRegion backUp;
	   TextureRegion backDown;
	   TextureRegion backbuttonUp;
	   TextureRegion backbuttonDown;
	   Texture tex;
	   ImageButton backbutton;
	  
	  
	Modelscreen(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/ModeSelection.png"));
		tex = new Texture(Gdx.files.classpath("imgs/button.png"));
		
		
	   
	     
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
		

		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			
			game.setScreen(game.LevelScreen1);
		}
	
		Gdx.gl.glClearColor(1, 1, 1, 1);
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 
		 batch.begin();
			batch.draw(texture, 0, 0);
			batch.end();
	       
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
