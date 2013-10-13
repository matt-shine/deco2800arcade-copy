package deco2800.arcade.breakout;
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


 
public class modelscreen implements Screen  {
	private final Breakout game;
	private final SpriteBatch batch;
	//private final Texture texture;
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
	  
	  
	modelscreen(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		//texture = new Texture(Gdx.files.classpath("imgs/menu.png"));
		tex = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 130, 45);
		
	    //backbutton
	    backbuttonUp=tmp[2][2];
	    backbuttonDown=tmp[2][3];
	    backup = new TextureRegionDrawable(backbuttonUp);
	    backdown = new TextureRegionDrawable(backbuttonDown);
	    backbutton = new ImageButton(backup, backdown);
	    backbutton.setPosition(480, 290);
	    backbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.MenuScreen); 
	        		
	        	}}
	    	   );
	  
	  
	       stage = new Stage(480, 640, true);
	       //Gdx.input.setInputProcessor(stage);
	     
	      stage.addActor(backbutton);
	     
	}
	
	
	  
	
	@Override
	public void dispose() {
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
