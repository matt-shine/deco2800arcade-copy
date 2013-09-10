package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.GameMap;
import deco2800.arcade.burningskies.entities.PlayerShip;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private Stage stage;
	
	private PlayerShip player;
	private GameMap map;
	
	public PlayScreen( BurningSkies game){
		this.game = game;
	}	
	 
    @Override
    public void show()
    {
    	// Initialising variables
		this.stage = new Stage( BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT, true);

		// Setting up the camera view for the game
		camera = (OrthographicCamera) stage.getCamera();
    	camera.setToOrtho(false, BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT);
    	camera.update();
    	
        game.playSong("level1");
    	
    	Texture shiptext = new Texture(Gdx.files.internal("images/Jet1.png"));
    	player = new PlayerShip(100, shiptext, new Vector2(400, 100));
    	map = new GameMap("fixme");
    	stage.addActor(player);
    	stage.addActor(map);
    }
    
    @Override
    public void hide() {
    	//TODO: Make sure this resets properly
    	stage.dispose();
    } 
    
    @Override
    public void render(float delta)
    {    	
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    	if(!game.isPaused()) {
    		stage.act(delta);
    	}
    	// Draws the map
    	stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
    }
}
