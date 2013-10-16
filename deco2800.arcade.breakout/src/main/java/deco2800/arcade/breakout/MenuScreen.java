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
	   TextureRegionDrawable rankingup;
	   TextureRegionDrawable rankingdown;
	   TextureRegionDrawable achivementup;
	   TextureRegionDrawable achivementdown;
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
	   TextureRegion achivementbuttonUp;
	   TextureRegion achivementbuttonDown;
	   TextureRegion quitbuttonUp;
	   TextureRegion quitbuttonDown;
	   TextureRegion modelbuttonUp;
	   TextureRegion modelbuttonDown;
	   
	   
	   Texture tex;
	   ImageButton gamebutton;
	   ImageButton levelbutton;
	   ImageButton rankingbutton;
	   ImageButton achivementbutton;
	   ImageButton quitbutton;
	   ImageButton modelbutton;
	   
	   
	
	menuscreen(final Breakout game) {
		
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/menu.png"));
		tex = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 130, 45);
		
		
	    newgamebuttonUp = tmp[0][0];
	    newgamebuttonDown = tmp[0][1];
	    gameup = new TextureRegionDrawable(newgamebuttonUp);
	    gamedown = new TextureRegionDrawable(newgamebuttonDown);
	    gamebutton = new ImageButton(gameup, gamedown);
	    gamebutton.setPosition(480, 470);
	    gamebutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.gamescreen);
	        	}}
	    	   );
	   //model screen
	    
	    
 
	    modelbuttonUp=tmp[3][0];
	    modelbuttonDown=tmp[3][1];
	    modelup = new TextureRegionDrawable(modelbuttonUp);
	    modeldown = new TextureRegionDrawable(modelbuttonDown);
	    modelbutton = new ImageButton(modelup, modeldown);
	    modelbutton.setPosition(480, 400);
	    modelbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.modelscreen); 
	        		
	        	}}
	    	   );
	  //level screen
	    levelbuttonUp=tmp[0][2];
	    levelbuttonDown=tmp[0][3];
	    levelup = new TextureRegionDrawable(levelbuttonUp);
	    leveldown = new TextureRegionDrawable(levelbuttonDown);
	    levelbutton = new ImageButton(levelup, leveldown);
	    levelbutton.setPosition(480, 340);
	    levelbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.LevelScreen); 
	        		
	        	}}
	    	   );
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
	    //achivement
	    achivementbuttonUp=tmp[1][2];
	    achivementbuttonDown=tmp[1][3];
	    achivementup = new TextureRegionDrawable(achivementbuttonUp);
	    achivementdown = new TextureRegionDrawable(achivementbuttonDown);
	    achivementbutton = new ImageButton(achivementup, achivementdown);
	    achivementbutton.setPosition(480, 200);
	    achivementbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		game.setScreen(game.AchivementScreen); 
	        		
	        	}}
	    	   );
	    //quit
	    quitbuttonUp=tmp[2][0];
	    quitbuttonDown=tmp[2][1];
	    quitup = new TextureRegionDrawable(quitbuttonUp);
	    quitdown = new TextureRegionDrawable(quitbuttonDown);
	    quitbutton = new ImageButton(quitup, quitdown);
	    quitbutton.setPosition(480, 130);
	    quitbutton.addListener(new InputListener(){
	    	   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
	        		
	        		return true; 
	        	}
	        	
	        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
	        		
	        		ArcadeSystem.goToGame(ArcadeSystem.UI);
	        	}}
	    	   );
	    
	    

	       stage = new Stage(480, 640, true);
	      // gamebutton.setWidth(400f);
	      // gamebutton.setHeight(100f);
	      // quitbutton.setWidth(200f);
	      // quitbutton.setHeight(50f);
	       
	       
	       
	       stage.addActor(gamebutton);
	       stage.addActor(quitbutton);
	       stage.addActor(levelbutton);
	       stage.addActor(rankingbutton);
	       stage.addActor(achivementbutton);
	       stage.addActor(modelbutton);
	       
	       
		
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
		Gdx.input.setInputProcessor(stage);
		
	}

	
}
