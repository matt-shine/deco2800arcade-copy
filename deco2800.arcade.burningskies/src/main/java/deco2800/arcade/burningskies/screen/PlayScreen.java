package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.Enemy;
import deco2800.arcade.burningskies.entities.Entity;
import deco2800.arcade.burningskies.entities.Level;
import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.PowerUp;
import deco2800.arcade.burningskies.entities.DemoPowerUp;
import deco2800.arcade.burningskies.entities.HealthPowerUp;
import deco2800.arcade.burningskies.entities.bullets.Bullet;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.client.ArcadeInputMux;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private Stage stage;
	private ShapeRenderer debugRender;
	private ShapeRenderer healthBar;
	private PlayerInputProcessor processor;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	private int health = 100;
	
	private Color healthBarRed = new Color(1, 0, 0, 1);
	private Color healthBarOrange = new Color(1, (float)0.65, 0, 1);
	private Color healthBarGreen = new Color(0, 1, 0, 1);	
	
	private static final int width = BurningSkies.SCREENWIDTH;
    private static final int height = BurningSkies.SCREENHEIGHT;
    
	private final int healthBarLengthMultiplier = 7;
	private final float healthBarWidth = (float) (height * 0.02);
	private final float healthBarHeight = health * healthBarLengthMultiplier;
	private final float healthBarX = (float) (width * 0.985);
	private final float healthBarY = height/2 - (healthBarHeight)/2;
	
	private PlayerShip player;
	public Level level;
	
	private SpawnList sp;

	private float respawnTimer = 0f;;
	
	
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
    	
    	debugRender = new ShapeRenderer();
    	debugRender.setProjectionMatrix(camera.combined);
    	
    	healthBar = new ShapeRenderer();
    	healthBar.setProjectionMatrix(camera.combined);
    	
        game.playSong("level1");
    	
    	Texture shiptext = new Texture(Gdx.files.internal("images/ships/jet.png"));
    	player = new PlayerShip(100, shiptext, new Vector2(400, 100), this);
    	level = new Level("fixme");

    	stage.addActor(level);
    	stage.addActor(player);
    	
    	processor = new PlayerInputProcessor(player);
    	ArcadeInputMux.getInstance().addProcessor(processor);
    	
    	// Test code
    	PowerUp test = new DemoPowerUp(this, "images/items/health.png");
    	addPowerup(test);
    	test = new HealthPowerUp("images/items/health.png");
    	addPowerup(test);
    	
    	sp = new SpawnList(this);
    			
    	// Add an enemy
    	addRandomEnemy();
    }
    
    @Override
    public void hide() {
    	//TODO: Make sure this resets properly
    	ArcadeInputMux.getInstance().removeProcessor(processor);
    	game.stopSong();
    	stage.dispose();
    }
    
    @Override
    public void render(float delta)
    {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    	
    	
    	
    	if(!game.isPaused()) {
    		
    		if(!player.isAlive()) {
    			respawnTimer -= delta;
    			if(respawnTimer <= 0) {
    				stage.addActor(player);
    				player.respawn();
    			}
    		}

    		sp.checkList(delta);
    		
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
    					if(outOfBounds(e)) {
    						removeEntity(e);
    						j--;
    						continue;
    						
    					}
    					if(e.isAlive() && b.hasCollided(e)) { // must check if alive if they're playing the explode animation
    						e.damage(b.getDamage());
    						if(!e.isAlive()) {
    							removeEntity(e);
    						}
    						removeEntity(b);
//    	        			i--; TODO this was causing trouble after making the SpawnList class, may need this later
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
				if(e.hasCollided(player) && player.isAlive()) {
					removeEntity(e);
					i--;
					player.damage(40);
				}
			}	
    	}
    	    	
    	// Draws the map
    	stage.draw();
    	healthBar.begin(ShapeType.FilledRectangle);
    	
    	health = player.getHealth();
    	
    	if (health <= 25) {
    		healthBar.setColor(healthBarRed);
    	} else if (health > 25 && health <= 75) {
    		healthBar.setColor(healthBarOrange);
    	} else {
    		healthBar.setColor(healthBarGreen);
    	}
    	
    	healthBar.filledRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);    	
    	healthBar.end();
    	
    	if(player.isAlive()) {
	    	debugRender.begin(ShapeType.FilledCircle);
	    	float[] hitbox = player.getHitbox().getTransformedVertices();
	    	for(int i=0;i<hitbox.length;i+=2) {
	    		debugRender.filledCircle(hitbox[i], hitbox[i+1], 3);
	    	}
	    	debugRender.end();
    	}
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

	public void killPlayer() {
		player.remove();
		respawnTimer  = 2f;
	}

	public PlayerShip getPlayer() {
		return player;
	}
}
