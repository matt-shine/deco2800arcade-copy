package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
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
import deco2800.arcade.burningskies.entities.DemoPowerUp;
import deco2800.arcade.burningskies.entities.bullets.Bullet;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.client.ArcadeInputMux;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private Stage stage;
	private PlayerInputProcessor processor;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	
	private PlayerShip player;
	private GameMap map;
	
	private float timerCounter;
	private float enemyInterval = 2;
	
	
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
    	
    	Texture shiptext = new Texture(Gdx.files.internal("images/ships/jet.png"));
    	player = new PlayerShip(100, shiptext, new Vector2(400, 100), this);
    	map = new GameMap("fixme");

    	stage.addActor(map);
    	stage.addActor(player);
    	
    	processor = new PlayerInputProcessor(player);
    	ArcadeInputMux.getInstance().addProcessor(processor);
    	
    	// Test code
    	PowerUp test = new DemoPowerUp(this);
    	addPowerup(test);
    	
    	// Add an enemy
    	addRandomEnemy();
    	timerCounter = 0;
    }
    
    @Override
    public void hide() {
    	//TODO: Make sure this resets properly
    	ArcadeInputMux.getInstance().removeProcessor(processor);
    	stage.dispose();
    }
    
    @Override
    public void render(float delta)
    {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	
    	if(!game.isPaused()) {
    		//Increment timer
    		timerCounter += delta;
    		if (timerCounter >= enemyInterval){
    			addRandomEnemy();
    			timerCounter -= enemyInterval;
    		}
    		
    		stage.act(delta);
    		for(int i=0; i<bullets.size(); i++) {
    			Bullet b = bullets.get(i);
        		if(outOfBounds(b)) {
        			removeEntity(b);
        			i--;
        			continue;
        		}
        		//deal with collisions
    			if(b.getAffinity() == Affinity.PLAYER) {
    				for(int j=0; j<enemies.size(); j++) {
    					Enemy e = enemies.get(j);
    					if(e.isAlive() && b.hasCollided(e)) { // must check if alive if they're playing the explode animation
    						e.damage(b.getDamage());
    						if(!e.isAlive()) {
    							removeEntity(e);
    							//addEnemy(new Enemy(200, testTex, new Vector2(300,400), this)); //TODO: DEBUG, REMOVE LATER
    						//	addRandomEnemy();
    						}
    						removeEntity(b);
    	        			i--;
    						continue;
    					}
    					
    				}
    			} else if(b.hasCollided(player)) {
    				//TODO: DAMAGE THE PLAYER YOU NUGGET
    				player.damage(b.getDamage());
    				//TODO: Player death/respawn checker.
    				//if (!player.isAlive()) { }
    				removeEntity(b);
        			i--;
    				continue;
    			}
    		}
			for(int i=0; i<powerups.size(); i++) {
				PowerUp p = powerups.get(i);
				if(p.hasCollidedUnscaled(player)) {
					//TODO: POWERUPS WOO
					p.powerOn(player);
					removeEntity(p);
					i--;
					continue;
				}
			}
			// checks if the enemy is out of screen, if so remove it
			for(int i=0; i<enemies.size(); i++) {
				Enemy e = enemies.get(i);
				if(outOfBounds(e)) {
					removeEntity(e);
					i--;
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
    
    public void addEnemy(Enemy enemy) {
    	stage.addActor(enemy);
    	enemies.add(enemy);
    }
    
    private void addRandomEnemy() {
    	Texture testTex = new Texture(Gdx.files.internal("images/ships/enemy1.png"));
    	float startX = (float) Math.ceil(Math.random() * 1000) + 100;
    	float startY = (float) 700;
    	int direction;
    	
    	if((int) Math.floor(Math.random() * 2) == 1 )
    		direction = 1;
    	else
    		direction = -1;
    	
    	float vX = (float) Math.ceil(Math.random() * 75 * direction) ;
    	float vY = (float) Math.ceil(Math.random() * -150) - 50;
    	System.out.println("x: " + startX + ",y: " + startY + ",vX: " + vX + ",vY: " + vY);
    	addEnemy(new Enemy(200, testTex, new Vector2(startX,startY), this, new Vector2(vX,vY)) );    	
    }
    
    public void addPowerup(PowerUp p) {
    	stage.addActor(p);
    	powerups.add(p);
    }
    
    public void removeEntity(Bullet b) {
    	b.remove();
		bullets.remove(b);
    }
    
    public void removeEntity(Enemy e) {
    	e.remove();
    	enemies.remove(e);
    }
    
    public void removeEntity(PowerUp p) {
    	p.remove();
    	powerups.remove(p);
    }
}
