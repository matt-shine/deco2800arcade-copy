package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.Enemy;
import deco2800.arcade.burningskies.entities.Entity;
import deco2800.arcade.burningskies.entities.GameMap;
import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.PowerUp;
import deco2800.arcade.burningskies.entities.bullets.Bullet;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private Stage stage;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
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
    	
    	Texture shiptext = new Texture(Gdx.files.internal("images/jet_debug.png"));
    	player = new PlayerShip(100, shiptext, new Vector2(400, 100), this);
    	map = new GameMap("fixme");
    	
    	// Test code
    	PowerUp test = new PowerUp();

    	stage.addActor(map);
    	stage.addActor(player);
    	
    	// Test code
    	stage.addActor(test);
    }
    
    @Override
    public void hide() {
    	//TODO: Make sure this resets properly
    	stage.dispose();
    }
    
    @Override
    public void render(float delta)
    {
    	//concurrency whoooo!
    	bullets.removeAll(bulletsToRemove);
    	bulletsToRemove.clear();
    	
    	if(!game.isPaused()) {
    		stage.act(delta);
    		for(Bullet b : bullets) {
        		if(outOfBounds(b)) {
        			removeBullet(b);
        			continue;
        		}
        		//deal with collisions
    			if(b.getAffinity() == Affinity.PLAYER) {
    				for(Enemy e : enemies) {
    					if(b.hasCollided(e)) {
    						//TODO: HANDLE DAMAGE FROM THIS YA MUPPETS
    						removeBullet(b);
    						continue;
    					}
    				}
    			} else if(b.hasCollided(player)) {
    				//TODO: DAMAGE THE PLAYER YOU NUGGET
    				removeBullet(b);
    				continue;
    			}
    		}
    	}
    	// Draws the map
    	stage.draw();
    }
    
    private boolean outOfBounds(Entity e) {
    	// are we in bounds? if not, goodbye
		float left = e.getX() + e.getWidth();
		float right = e.getY() + e.getHeight();
		// 10 pixels in case they're flying off the sides
		if(left < -10 || right < -10 || e.getX() > stage.getWidth() + 10 || e.getY() > stage.getHeight() + 10) {
			return true;
		} 
		return false;
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
    
    public void addBullet(Bullet bullet) {
    	stage.addActor(bullet);
    	bullets.add(bullet);
    }
    
    public void removeBullet(Bullet bullet) {
    	bulletsToRemove.add(bullet);
    	bullet.remove();
    }
    
    public void addEnemy(Enemy enemy) {
    	stage.addActor(enemy);
    	enemies.add(enemy);
    }
}
