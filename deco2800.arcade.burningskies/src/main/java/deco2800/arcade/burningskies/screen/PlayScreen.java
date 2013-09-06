package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.PlayerShip;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	private Music music;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Stage stage;
	
	private int x = 0;
	private float y = 0;
	private int speed = 40;
	
	private PlayerShip player;
	
	public PlayScreen( BurningSkies game){
		this.game = game;
	}	
	 
    @Override
    public void show()
    {
    	// Initialising variables
    	texture = new Texture( Gdx.files.internal("maps/test2.png"));
		batch = new SpriteBatch();
		this.stage = new Stage( BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT, true , batch);

		// Setting up the camera view for the game
		camera = (OrthographicCamera) stage.getCamera();
    	camera.setToOrtho(false, BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT);
    	camera.update();
    	
        game.playSong("level1");
    	
    	Texture shiptext = new Texture(Gdx.files.internal("images/Jet1.png"));
    	player = new PlayerShip(100, shiptext, new Vector2(400, 100));
    	stage.addActor(player);
    }
    
    @Override
    public void hide() {
    	music.stop();
    } 
    
    @Override
    public void render(float delta)
    {    	
    	// auto scroll
    	y -= (float) Gdx.graphics.getDeltaTime() * speed;
    	
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	
    	// Draws the map
    	batch.begin();
    	batch.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight() );
    	batch.end();
    	
    	stage.act(delta);
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
