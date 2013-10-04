package deco2800.arcade.platformergame.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityCollection implements Iterable<Entity> {
	/**
	 * Store a collection of entities, and maintain a unique id for each one
	 * Expose HashMap methods here as needed 
	 */
	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	
	public EntityCollection() {
		
	}
	
	private int generateId() {
		//Get a random integer that isn't in the key set
		int id;
		Random random = new Random();
		while (entities.containsKey(id = random.nextInt()));
		return id;
	}
	
	public void remove(Entity e){
		entities.remove(e);
	}
	
	public void add(Entity e) {
		//Add an entity to the entities map, with a unique id
		entities.put(generateId(), e);
	}

	
	@Override
	public Iterator<Entity> iterator() {
		return entities.values().iterator();
	}
	
	public void updateAll(float delta) {
		for (Entity e : this) {
			e.update(delta);
		}
	}
	
	public void drawAll(SpriteBatch batch, float stateTime) {
		for (Entity e : this) {
			e.draw(batch, stateTime);
		}
	}

    public int size() {
        return entities.size();
    }
}