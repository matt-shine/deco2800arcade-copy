package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
	private TiledLayer collisionLayer;
	
	public RandomizedEnemySpawner(Array<EnemySpawner> enemySpawners, boolean[] rightSideOfScreen, float rate) {
		this(enemySpawners, rightSideOfScreen, null, rate, 0, 9999f);
	}
	
	public RandomizedEnemySpawner(Array<EnemySpawner> enemySpawners, boolean[] rightSideOfScreen, TiledLayer collisionLayer, 
			float rate, float startRange, float endRange) {
		this.enemySpawners = enemySpawners;
		this.rightSideOfScreen = rightSideOfScreen;
		this.rate = rate;
		count = 0;
		active = false;
		this.startRange = startRange;
		this.endRange = endRange;
		this.collisionLayer = collisionLayer;
	}
	
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void removeEnemy(Enemy e) {
		for (EnemySpawner es: enemySpawners) {
			es.removeEnemy(e);
		}
	}
	
	public Enemy update(float delta, Camera cam, float rank) {
		if (active && cam.position.x > startRange && cam.position.x < endRange) {
			count += delta;
			if (count > rate) {
				
				count = 0;
				
				Enemy output = null;
				for (int i=0; i < 3; i++) {
					//pick an enemy spawner at random to spawn from
					int rand = MathUtils.random(enemySpawners.size-1);
					EnemySpawner es = enemySpawners.get(rand);
					
					//adjust position to just outside camera
					float originalY = es.getPosition().y;
					if (rightSideOfScreen[rand]) {
						es.setPosition(new Vector2 (cam.position.x + cam.viewportWidth/2 + 2f, es.getPosition().y));
					} else {
						es.setPosition(new Vector2 (cam.position.x - cam.viewportWidth/2 - 2f, es.getPosition().y));
					}
					if (collisionLayer != null) {
						
						for (float yChange = es.getPosition().y; yChange < es.getPosition().y + 25f; yChange+=0.5f) {
							boolean collision = false;
							for (float xRange = es.getPosition().x - 1f; xRange < es.getPosition().x+2f; xRange+=1f) {
								
								int yLength = collisionLayer.tiles.length;
								if ((int)xRange < 0) xRange = 0;
								int cell = collisionLayer.tiles[yLength-((int)yChange)-1][(int)xRange];
								
								
								if (cell != 0) {
									collision = true;
									
									break;
									
								}
								
								
							}
							if (!collision) {
								es.getPosition().y = yChange;
								output = es.spawnNewIfPossible();
								break;
							}
						}
					}
					es.getPosition().y = originalY;
					if (output != null) {
						break;
					}
				}
				return output;
			}
		}
		
		return null;
	}
}
