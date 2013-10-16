package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.Enemy;
import deco2800.arcade.burningskies.entities.HealthPowerUp;
import deco2800.arcade.burningskies.entities.PatternPowerUp;
import deco2800.arcade.burningskies.entities.PowerUp;
import deco2800.arcade.burningskies.entities.SpeedPowerUp;

public class SpawnList {
	
	private PlayScreen screen;
	private float currentInterval;
	private float interval;
	private List<Object> list;
	private int occurence = 0;
	private PowerUp currentPowerUp = null;
	
	private static final long standardEnemyPoints = 1006493;
	
	// TODO more variable pointing to other types of enemies
	private Texture enemy1 = new Texture(Gdx.files.internal("images/ships/enemy1.png"));
	
	public SpawnList(PlayScreen s){
		this.screen = s;
		currentInterval = 0;
		interval = (float) 2;		
		list = new ArrayList<Object>();
		makeList1();
	}
	
	/* Just a dummy function, but still keep
	 * TODO make each function call setup a
	 * level for enemy spawn sequence
	 */
	private void makeList1() {
		list.add((float) 2); // when to spawn on map
		list.add((float) 1); // interval
		list.add((int) 10); // number of times
		Vector2[] test = new Vector2[2];
		test[0] = new Vector2(1000, 600); // x and y position
		test[1] = new Vector2(-50,-50); // x and y velocity
		list.add(test); 
	}
	
	/* Main function that spawn the enemies at specified intervals
	 * The conditional to check for screen.map.getY() can be further
	 * improved TODO make a list in order contain the spawn sequence of 
	 * enemies
	 */
	public void checkList(float delta) {
		// TODO make an auto queue for the enemies
//		if(screen.level.getTimer() > (Float) list.get(0) && mapCounter != 0)  {
//			interval = (Float) list.get(1);
//			counter = (Integer) list.get(2);
//			mapCounter = 0;
//		}

//		if(interval == (float) 0 || counter == 0)
//			return;
		
		if(currentInterval >= interval) {
//			spawnEnemy(list.get(3));
//			--counter;
			interval -= 0.1;
			if(interval < 0.25) {
				interval = (float) 0.25;
			}
			
			addRandomEnemy();
			currentInterval -= interval;
		}
		currentInterval += delta;
	}

	/**
	 * Automatically random spawn an enemy around the edge of the screen
	 */
	private void addRandomEnemy() {
			float startX = (float) 0;
			float startY = (float) 0;
			
			float widthC = (float) 740;
			float heightC = (float) 360;
					
			int direction = (int) Math.ceil(Math.random() * 4);
			
			
			// Determine where the enemy will start to spawn
			switch (direction) {
			case 1: // top
				startY = (float) 720;
				startX = (float) Math.ceil(Math.random() * 1000) + 100;
				break;
			case 2: // right
				startX = 1280;
				startY = (float) Math.ceil(Math.random() * 600) + 50;
				break;
			case 3: // bottom
				startY = 0;
				startX = (float) Math.ceil(Math.random() * 1000) + 100;
				break;
			case 4: // left
				startX = 0;
				startY = (float) Math.ceil(Math.random() * 600) + 50;
				break;			
			}
			
			// Randomly set the x and y velocity
			float vX = (float) Math.ceil(Math.random() * (widthC - startX))/10 + (widthC - startX)/10;
			float vY = (float) Math.ceil(Math.random() * (heightC - startY))/7 + (heightC - startY)/5;

			// Add the enemy to the screen
			screen.addEnemy(new Enemy(200, enemy1, new Vector2(startX,startY), new Vector2(vX, vY), screen, screen.getPlayer(), standardEnemyPoints) );    	
	}
	
	private void addRandomPowerUp() {
		ArrayList<PowerUp> powers = new ArrayList<PowerUp>();
		float X = (float)Math.random()*12800;
		float Y = (float)Math.random()*720;
		powers.add(new HealthPowerUp(X, Y));
		powers.add(new SpeedPowerUp(X, Y));
		powers.add(new PatternPowerUp(screen, X, Y));
		PowerUp toAdd = powers.get((int)Math.round(Math.random()*2));
		if (occurence >= 3) {
			powers.remove(currentPowerUp);
			toAdd = powers.get((int)Math.round(Math.random()));
			this.occurence = 0;
		}
		if (toAdd.equals(currentPowerUp)) {
			this.occurence++;
		} else {
			currentPowerUp = toAdd;
			this.occurence = 1;
		}
		screen.addPowerup(toAdd);
	}
}
