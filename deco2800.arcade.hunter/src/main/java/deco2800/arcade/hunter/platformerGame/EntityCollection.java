package deco2800.arcade.hunter.platformerGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EntityCollection implements Iterable<Entity> {
    /**
     * Store a collection of entities, and maintain a unique id for each one
     * Expose HashMap methods here as needed
     */
    private final ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();

    private int generateId() {
        //Get a random integer that isn't in the key set
        int id;
        Random random = new Random();
        while (entities.containsKey(id = random.nextInt()) || id < 0);
        return id;
    }

    public void remove(Entity e) {
        Iterator itr = iterator();
        while (itr.hasNext()) {
            Entity currEn = (Entity) itr.next();
            if (currEn.equals(e)) {
                itr.remove();
            }
        }
    }

    public int add(Entity e) {
        //Add an entity to the entities map, with a unique id
        int id = generateId();
        entities.put(id, e);
        return id;
    }


    @Override
    public Iterator<Entity> iterator() {
        return entities.values().iterator();
    }

    public Iterable<Integer> idSet() {
        return entities.keySet();
    }

    public void updateAll(float delta) {
        Iterator<Entity> e = iterator();
        while (e.hasNext()) {
            e.next().update(delta);
        }
    }

    public void drawAll(SpriteBatch batch, float stateTime) {
        Iterator<Entity> e = iterator();
        while (e.hasNext()) {
            e.next().draw(batch, stateTime);
        }
    }

    public Entity getById(int id) {
        return entities.get(id);
    }

    public int size() {
        return entities.size();
    }
}