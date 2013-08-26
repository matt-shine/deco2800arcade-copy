package deco2800.arcade.burningskies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PlayGame implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Sprite sprite;
	private Texture texture;
	
	private int x = 0;
	private float y = 0;
	private int speed = 40;
	
	public PlayGame( BurningSkies game){
		this.game = game;		
	}	
	 
    @Override
    public void show()
    {
    	texture = new Texture( Gdx.files.internal("maps/test2.png"));
		
		//texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		sprite = new Sprite(texture);
		//sprite.setSize(200, 400); //1280);
		//sprite.setPosition(200, 100);
		
		batch = new SpriteBatch();
    	camera = new OrthographicCamera();
    	camera.setToOrtho(false, game.SCREENWIDTH, game.SCREENHEIGHT);
    	camera.update();
    }
    
    @Override
    public void hide() {
    	
    } 
    
    @Override
    public void render(float delta)
    {
    	// Reading the up or down arrow key to move the map in that direction
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
    	
    	//create();
    	
    	//Gdx.gl.glDisable(GL10.GL_CULL_FACE);
    	
    	// Draws the map
    	batch.begin();
    	batch.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight() );
    	batch.end();

    	//Gdx.gl.glEnable(GL10.GL_CULL_FACE);
    	
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
