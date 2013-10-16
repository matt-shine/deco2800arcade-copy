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
					
					//adjust position to just outside camera (WILL NEED TO ALSO ADJUST HEIGHT BUT I DON'T KNOW HOW YET)
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
								//if (i < xLength && i > 0 && j < yLength && j > 0) { 
								//int cell = collisionLayer.tiles[yLength-((int)yChange)-1][(int)es.getPosition().x];
								int cell = collisionLayer.tiles[yLength-((int)yChange)-1][(int)xRange];
								
								/*String type = map.getTileProperty(cell, "checkCollision");
								if (type != null && type.equals("solid")) {*/
								
								
								//this won't work becaus it only checks single tiles collisions. Unless you just combine inline and collision layers
								if (cell != 0) {
									//Rectangle rect = new Rectangle((int)es.getPosition().x, (int)yChange, 1, 1);
									//if (rect.overlaps(new Rectangle(es.getPosition().x, es.getPosition().y, 1,1))){
										collision = true;
										System.out.println("@@@@@@@@Collision at "+yChange+","+xRange);
										break;
									//}
								}
								
								
							}
							if (!collision) {
								es.getPosition().y = yChange;
								System.out.println("Spawning new from randomized at "+ es.getPosition() + "campos="+cam.position.x+" camview="+cam.viewportWidth);
								output = es.spawnNewIfPossible();
								break;
							}
						}
					}
					es.getPosition().y = originalY;
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
