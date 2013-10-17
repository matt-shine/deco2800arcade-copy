package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.entities.Boss;
import deco2800.arcade.burningskies.entities.Enemy;
import deco2800.arcade.burningskies.entities.Entity;
import deco2800.arcade.burningskies.entities.Level;
import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.PowerUp;
import deco2800.arcade.burningskies.entities.bullets.Bullet;
import deco2800.arcade.burningskies.entities.bullets.Bullet.Affinity;
import deco2800.arcade.client.ArcadeInputMux;


public class PlayScreen implements Screen
{
	private BurningSkies game;
	
	private OrthographicCamera camera;
	private Stage stage;
	private ShapeRenderer healthBar;
	private PlayerInputProcessor processor;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	private SpriteBatch batch;
	private Label scoreLabel;
    private BitmapFont white;
	
	private Color healthBarRed = new Color(1, 0, 0, 1);
	private Color healthBarOrange = new Color(1, (float)0.65, 0, 1);
	private Color healthBarGreen = new Color(0, 1, 0, 1);	
	
	private static final int width = BurningSkies.SCREENWIDTH;
    private static final int height = BurningSkies.SCREENHEIGHT;
    
	private float health = 100;
	private int healthBarLengthMultiplier = 7;
	private int healthBarStaticHeight = 100 * healthBarLengthMultiplier;
	private float healthBarHeight = health * healthBarLengthMultiplier;
	private float healthBarWidth = (float) (height * 0.02);
	private float healthBarX = (float) (width * 0.985);
	private float healthBarY = height/2 - (healthBarHeight)/2;
	
	private int lives = 3;
	private float lifePositionX = 10;
	private float lifePositionY = height - 70;
	private static Texture lifeIcon = new Texture(Gdx.files.internal("images/misc/jet_life_icon.png"));
	private float lifePositionOffset = (float) (lifeIcon.getWidth() + lifeIcon.getHeight() * 0.1);
	
	private long score = 0;
	
	private static Texture[] shipTex = {
			new Texture(Gdx.files.internal("images/ships/jet.png")),
			new Texture(Gdx.files.internal("images/ships/secret.cim"))
	};
	private PlayerShip player;
	public Level level;
	
	private boolean bossActive = false;
	private SpawnList sp;

	private float respawnTimer = 0f;
	private float levelTimer = 0f;
	
	
	public PlayScreen( BurningSkies game){
		this.game = game;
	}	
	 
    @Override
    public void show()
    {
    	// Initialising variables
		this.stage = new Stage( BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT, true);
		white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);

		LabelStyle scoreLabelStyle = new LabelStyle(white, Color.WHITE);
		scoreLabel = new Label("Scores: " + score, scoreLabelStyle);
		scoreLabel.setX(10);
		scoreLabel.setY((float)(height*0.95));
		scoreLabel.setWidth(0);
		batch = new SpriteBatch();
		
		// Setting up the camera view for the game
		camera = (OrthographicCamera) stage.getCamera();
    	camera.setToOrtho(false, BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT);
    	camera.update();
    	
    	healthBar = new ShapeRenderer();
    	healthBar.setProjectionMatrix(camera.combined);
    	
        game.playSong("level" + (int)(Math.random()+0.5));
    	
    	player = new PlayerShip(400000000, shipTex[game.zalgo], new Vector2(400, 100), this);
    	level = new Level(this);

    	stage.addActor(level);
    	stage.addActor(player);
    	stage.addActor(scoreLabel);
    	
    	processor = new PlayerInputProcessor(player);
    	ArcadeInputMux.getInstance().addProcessor(processor);
    	
    	sp = new SpawnList(this);
    }
    
    @Override
    public void hide() {
    	ArcadeInputMux.getInstance().removeProcessor(processor);
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
    				if (lives > 0) {
    					player.respawn();
    					sp.setTimer((float) 2.5);
    					while(bullets.size() != 0) {
    						removeEntity(bullets.get(0));
    					}
    				} else {
    					// Create a game over screen
    					game.playSong("gameover");
    					game.setScreen(game.menuScreen);
    				}
    			}
    		} else {
    			levelTimer += delta;
    		}
    		//TODO: PRESS B TO SPAWN BOSS, REMOVE AFTER DEBUG
    		if(!bossActive && (levelTimer > 60 || Gdx.input.isKeyPressed(Keys.B))) { //unleash the beast
    			bossActive = true;
    			game.playSong("boss");
    			addEnemy(new Boss(this, player));
    		}

    		if(!bossActive) {
    			sp.checkList(delta);
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
    							score += e.getPoints();
    							removeEntity(e);
    						}
    						removeEntity(b);
//    	        			i--; TODO this was causing trouble after making the SpawnList class, may need this later
    						continue;
    					}
    					
    				}
    			} else if(b.hasCollided(player) && player.isAlive()) {
    				player.damage(b.getDamage());
    				removeEntity(b);
        			i--;
    				continue;
    			}
    		}
			for(int i=0; i<powerups.size(); i++) {
				PowerUp p = powerups.get(i);
				if(p.hasCollidedUnscaled(player) && player.isAlive()) {
					//TODO: POWERUPS WOO
					p.powerOn(player);
					removeEntity(p);
					i--;
					continue;
				}
			}
			for(int i=0; i<enemies.size(); i++) {
				Enemy e = enemies.get(i);
				if(e.hasCollided(player) && player.isAlive() && !(e instanceof Boss)) {
					removeEntity(e);
					i--;
					player.damage(e.getHealth());
				}
			}	
    	} 
    	    	
    	// Draws the map
    	stage.draw();
    	
    	for (int i = 0; i < lives; i++) {
	    	batch.begin();
	    	batch.draw(lifeIcon, lifePositionX + lifePositionOffset*i, lifePositionY);
	    	batch.end();
    	}
    	
    	healthBar.begin(ShapeType.FilledRectangle);
    	
//    	score += 131;
    	health = player.getHealth();
    	lives = player.getLives();
    	scoreLabel.setText("Scores: " + score);
    	
    	healthBarHeight = Math.max(0, healthBarStaticHeight * (player.getHealth()/player.getMaxHealth()) );
    	
//    	System.out.println("health: " + healthBarHeight + ",  player health: " + player.getHealth()  );
    	
    	if (player.getHealth() <= (player.getMaxHealth()/4) ) {
    		healthBar.setColor(healthBarRed);
    	} else if (player.getHealth() > (player.getMaxHealth()/4) && player.getHealth() <= (player.getMaxHealth()*3/4)) {
    		healthBar.setColor(healthBarOrange);
    	} else {
    		healthBar.setColor(healthBarGreen);
    	}
    	
    	healthBar.filledRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);    	
    	healthBar.end();
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
	
	public int zalgo() {
		return game.zalgo;
	}
}
