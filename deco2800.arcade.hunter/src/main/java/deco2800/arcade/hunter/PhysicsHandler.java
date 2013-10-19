package deco2800.arcade.hunter;

import deco2800.arcade.hunter.model.ForegroundLayer;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PhysicsHandler {
    /**
     * Checks for entity collisions
     */
    public static void checkEntityCollisions(EntityCollection entities) {
        /*
		 * Make a list of collision events
		 * At the end, process them all one after the other
		 * Precedence should be
		 *
		 * Player ->| left edge of screen
		 * Prey ->| left edge of screen
		 * Predator ->| left edge of screen
		 * Item ->| Player
		 * PlayerProjectile ->| Animal(player melee attacks also PlayerProjectiles)
		 * WorldProjectile ->| Player (animal melee attacks also WorldProjectiles)
		 * MapEntity ->| Animal
		 * MapEntity ->| WorldProjectile
		 * MapEntity ->| PlayerProjectile
		 * MapEntity ->| Player
		 */

        ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();

        for (Entity e : entities) {
            collisions.addAll(e.getCollisions(entities));
        }

        Collections.sort(collisions);

        for (EntityCollision c : collisions) {
            // Handle the collision
            c.getEntityOne().handleCollision(c.getEntityTwo(), entities);
        }
    }


    /**
     * Checks the collision of entities with the map
     *
     * @param entities        An entity collection that map collision will be checked for
     * @param foregroundLayer The foreground layer that is colliding with the entities
     */
    public static void checkMapCollisions(EntityCollection entities,
                                          ForegroundLayer foregroundLayer) {
        // Check map collisions for every entity that can collide with the map
        // (should be player, animals & projectiles)

        // Move the item so that it no longer intersects with the map
        Iterator<Entity> i = entities.iterator();
        while (i.hasNext()) {
            Entity e = i.next();
            e.getCollider().clear();

            int mid, colTop;
            mid = (int) (e.getX() + (e.getWidth() / 2));

            //Handle collision between the bottom of the entity and the map
            colTop = foregroundLayer.getColumnTop(mid);
            if (colTop > e.getY()) {
                e.setY(colTop);
                e.getCollider().bottom = true;
            }

            // Check for entities going outside the bounds of the map
//            int pane = foregroundLayer.getPane(e.getX());
//            if (pane != -1 && e.getY() <= foregroundLayer.getPaneOffset(pane)) {
//                System.out.println("Animal: ("+ e.getX() + ", " + e.getY() + ")");
//                i.remove();
//            }
        }
    }
}
