package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.DemoPattern;


public class PlayScreen implements Screen
{
	@SuppressWarnings("unused")
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture texture;
	private Stage stage;
	
	private int x = 0;
	private float y = 0;
	private int speed = 40;
	
	private DemoPattern test;
	
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
    	
    	// TO MAKE THINGS PRETTY FOR DEMO
    	test = new DemoPattern(stage, null);
    	test.start();
    }
    
    @Override
    public void hide() {
    	
    } 
    
    @Override
    public void render(float delta)
    {
    	// Reading the input key up or down arrow key to move the map in that direction
    	/*
    	if(Gdx.input.isKeyPressed(Keys.DPAD_UP)){
    		y -= (float) Gdx.graphics.getDeltaTime() * speed;
    		System.out.println("Key up pressed");
    		System.out.print("Current y value: ");
    		System.out.println(y);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)){
    		y += (float) Gdx.graphics.getDeltaTime() * speed;
    		System.out.println("Key down pressed");
    	}
    	*/
    	
    	// auto scroll
    	y -= (float) Gdx.graphics.getDeltaTime() * speed;
    	
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	
    	test.onRender(delta);
    	
    	// Draws the map
    	batch.begin();
    	batch.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight() );
    	batch.end();
    	
    	stage.act( delta );
    	// Remove entities outside the screen, we don't need them any more
    	float left, right;
    	for(Actor actor: stage.getActors()) {
    		left = actor.getX() + actor.getWidth();
    		right = actor.getY() + actor.getHeight();
    		if(left < 0 || right < 0 || actor.getX() > BurningSkies.SCREENWIDTH || actor.getY() > BurningSkies.SCREENHEIGHT) {
    			//TODO: Only remove bullets here, enemies maybe not
    			actor.remove();
    		}
    	}
    	stage.draw();    	
    }
    
    @Override
    public void resize(int width, int height) {
    	camera.viewportWidth = width;
    	camera.viewportHeight = height;
    	camera.update();
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