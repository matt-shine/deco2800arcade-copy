package deco2800.arcade.hunter.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import deco2800.arcade.platformergame.model.Entity;

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
	
	public void add(Entity e) {
		//Add an entity to the entities map, with a unique id
		entities.put(generateId(), e);
	}

	@Override
	public Iterator<Entity> iterator() {
		return entities.values().iterator();
	}
}