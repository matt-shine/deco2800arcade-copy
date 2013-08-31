package com.test.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RandomizedEnemySpawner {

	private Array<EnemySpawner> enemySpawners;
	private boolean[] rightSideOfScreen;
	private float rate;
	private float startRange;
	private float endRange;
	
	private float count;
	private boolean active;
	
	public RandomizedEnemySpawner(Array<EnemySpawner> enemySpawners, boolean[] rightSideOfScreen, float rate) {
		this(enemySpawners, rightSideOfScreen, rate, 0, 9999f);
	}
	
	public RandomizedEnemySpawner(Array<EnemySpawner> enemySpawners, boolean[] rightSideOfScreen, float rate, float startRange, float endRange) {
		this.enemySpawners = enemySpawners;
		this.rightSideOfScreen = rightSideOfScreen;
		this.rate = rate;
		count = 0;
		active = false;
		this.startRange = startRange;
		this.endRange = endRange;
	}
	
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Enemy update(float delta, Camera cam, float rank) {
		//System.out.println(active +" "+ cam.position.x+ " "+ startRange+ " "+endRange);
		if (active && cam.position.x > startRange && cam.position.x < endRange) {
			count += delta;
			//System.out.println("count="+ count+" rate="+rate);
			if (count > rate) {
				
				count = 0;
				
				Enemy output = null;
				for (int i=0; i < 3; i++) {
					//pick an enemy spawner at random to spawn from
					int rand = MathUtils.random(enemySpawners.size-1);
					EnemySpawner es = enemySpawners.get(rand);
					System.out.println("Spawning new from randomized at "+ es.getPosition());
					//adjust position to just outside camera (WILL NEED TO ALSO ADJUST HEIGHT BUT I DON'T KNOW HOW YET)
					if (rightSideOfScreen[rand]) {
						es.setPosition(new Vector2 (cam.position.x + cam.viewportWidth, es.getPosition().y));
					} else {
						es.setPosition(new Vector2 (cam.position.x, es.getPosition().y));
					}
					output = es.spawnNewIfPossible();
					if (output != null) {
						System.out.println("Spawning!!!!");
						break;
					} else {
						System.out.println("returned enemy was null");
					}
				}
				return output;
			}
		}
		
		return null;
	}
}
