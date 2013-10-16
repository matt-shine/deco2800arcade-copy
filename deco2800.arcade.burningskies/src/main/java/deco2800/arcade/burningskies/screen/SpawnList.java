package deco2800.arcade.burningskies.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.Enemy;

public class SpawnList {
	
	private PlayScreen screen;
	private float currentInterval;
	private float interval;
	private int counter;
	private int mapCounter;
	
	private List<Object> list;
	
	// TODO more variable pointing to other types of enemies
	private Texture enemy1 = new Texture(Gdx.files.internal("images/ships/enemy1.png"));
	
	public SpawnList(PlayScreen s){
		this.screen = s;
		currentInterval = 0;
		interval = (float) 0;		
		counter = 0;
		mapCounter = 1;
		list = new ArrayList<Object>();
		makeList1();
	}
	
	/* Just a dummy function, but still keep
	 * TODO make each function call setup a
	 * level for enemy spawn sequence
	 */
	private void makeList1() {
		list.add((float) -100); // when to spawn on map
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
		if(screen.map.getY() < (Float) list.get(0) && mapCounter != 0)  {
			interval = (Float) list.get(1);
			counter = (Integer) list.get(2);
			mapCounter = 0;
		}

		if(interval == (float) 0 || counter == 0)
			return;
		
		if(currentInterval >= interval) {
			spawnEnemy(list.get(3));
			--counter;
			currentInterval -= interval;
		}
		currentInterval += delta;
	}
	
	/* Still requires more complementary function calls
	 * to assist spawning specific types of enemies
	 */	
	private void spawnEnemy(Object object) {
		Vector2[] v = (Vector2[]) object;
		screen.addEnemy(new Enemy(200, enemy1, new Vector2(v[0].x, v[0].y),  screen, new Vector2(v[1].x, v[1].y)) );
	}

	/* Testing purposes, but still may still use later
	 */
	private void addRandomEnemy() {
	    	float startX = (float) Math.ceil(Math.random() * 1000) + 100;
	    	float startY = (float) 700;
	    	int direction;
	    	
	    	if((int) Math.floor(Math.random() * 2) == 1 )
	    		direction = 1;
	    	else
	    		direction = -1;
	    	
	    	float vX = (float) Math.ceil(Math.random() * 75 * direction) ;
	    	float vY = (float) Math.ceil(Math.random() * -150) - 50;
	    	screen.addEnemy(new Enemy(200, enemy1, new Vector2(startX,startY), screen, new Vector2(vX,vY)) );    	
	}

}
