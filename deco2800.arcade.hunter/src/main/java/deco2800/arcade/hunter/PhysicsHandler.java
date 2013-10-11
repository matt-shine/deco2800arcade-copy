package deco2800.arcade.hunter;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.ForegroundLayer;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;

import java.util.ArrayList;
import java.util.Collections;

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
	 * @param entities
	 *            An entity collection that map collision will be checked for
	 * @param foregroundLayer
	 *            The foreground layer that is colliding with the entities
	 */
	public static void checkMapCollisions(EntityCollection entities,
			ForegroundLayer foregroundLayer) {
		// Check map collisions for every entity that can collide with the map
		// (should be player, animals & projectiles)

		// Move the item so that it no longer intersects with the map

		for (Entity e : entities) {
			e.getCollider().clear();

			// Bottom edge
			checkMapCollisionBottom(e, foregroundLayer);

			// Right edge
			checkMapCollisionRight(e, foregroundLayer);

			// Top edge
			checkMapCollisionTop(e, foregroundLayer);
		}
	}

	/**
	 * Checks collision for a single entity's right edge with the map
	 * 
	 * @param e
	 *            The entity which is checked for collisions
	 * @param foregroundLayer
	 *            The foreground layer which it is checked against
	 */
	private static void checkMapCollisionBottom(Entity e,
			ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		boolean breakOut = false;

		for (int i = (int) right; i >= (int) e.getX(); i -= Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt(i, e.getY());
			float slopeHeight;

			switch (tile) {
			case 0:
				// Air tile
				break;
			case 1:
				// Solid tile, do something
				e.getCollider().bottom = true;
				e.setY((float) (Math.ceil(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE) + 0.5f);
				breakOut = true;
				break;
			case 2:
				// /_| slope
				if (i == (int) right) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
					if (e.getY() < slopeHeight) {
						e.setY(slopeHeight + 0.5f);
						e.getCollider().bottom = true;
					}
					breakOut = true;
				}
				break;
			case 3:
				// |_\ slope
				if (i <= (int) e.getX()) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e
							.getX() % Config.TILE_SIZE));
					if (e.getY() < slopeHeight) {
						e.setY(slopeHeight + 0.1f);
						e.getCollider().bottom = true;
					}

					breakOut = true;
				}
				break;
			case 4:
				// |-/ slope

				// TODO - cleanly remove this from the code base. No need for this collision type
				break;
			case 5:
				// \-| slope

				// TODO - cleanly remove this from the code base. No need for this collision type
				break;
			default:
				// Outside the map, or invalid tile.
				break;
			}

			if (breakOut) {
				break;
			}

		}
	}

	/**
	 * Checks collision for a single entity's top edge with the map
	 *
	 * @param e
	 *            The entity which will be checked for top map collision
	 * @param foregroundLayer
	 *            The layer which the entity will be checked against
	 */
	private static void checkMapCollisionTop(Entity e,
			ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		float top = e.getY() + e.getHeight();

		boolean breakOut = false;

		for (int i = (int) right; i >= (int) e.getX(); i -= Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt(i, top);
			float slopeHeight;
			switch (tile) {
			case 0:
				// Air tile
				break;
			case 1:
				// Solid tile, do something
				e.getCollider().top = true;
				e.setY((float) (Math.ceil((e.getY() - Config.TILE_SIZE)
						/ Config.TILE_SIZE) * Config.TILE_SIZE) - 0.5f);
				breakOut = true;
				break;
			case 2:
				// /_| slope
				if (i == (int) right) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
					if (e.getY() > slopeHeight) {
						e.setY(slopeHeight - 0.5f);
						e.getCollider().top = true;
					}
					breakOut = true;
				}
				break;
			case 3:
				// |_\ slope
				if (i <= (int) e.getX()) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e
							.getX() % Config.TILE_SIZE));
					if (e.getY() > slopeHeight) {
						e.setY(slopeHeight - 0.1f);
						e.getCollider().top = true;
					}

					breakOut = true;
				}
				break;
			case 4:
				// |-/ slope

				// TODO
				break;
			case 5:
				// \-| slope

				// TODO
				break;
			default:
				// Outside the map, or invalid tile.
				break;
			}

			if (breakOut) {
				break;
			}

		}
	}

	/**
	 * Checks collision for a single entity's right edge with the map
	 * 
	 * @param e
	 *            The entity that is checked for collision
	 * @param foregroundLayer
	 *            The layer which the entity will be checked against
	 */
	private static void checkMapCollisionRight(Entity e,
			ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		float top = e.getY() + e.getHeight();

		boolean breakOut = false;

		for (int i = (int) e.getY(); i <= top; i += Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt((int) right, i);
			float slopeHeight;
			switch (tile) {
			case 0:
				// Air tile
				break;
			case 1:
				// Solid tile, do something
				e.getCollider().right = true;
				e.setX((float) (Math.ceil((e.getX() - Config.TILE_SIZE)
						/ Config.TILE_SIZE) * Config.TILE_SIZE) - 0.5f);
				breakOut = true;
				break;
			case 2:
				// /_| slope
				if (i == (int) right) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
					if (e.getY() > slopeHeight) {
						e.setY(slopeHeight - 0.5f);
						e.getCollider().top = true;
					}
					breakOut = true;
				}
				break;
			case 3:
				// |_\ slope
				if (i <= (int) e.getX()) {
					slopeHeight = (float) (Math.floor(e.getY()
							/ Config.TILE_SIZE)
							* Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e
							.getX() % Config.TILE_SIZE));
					if (e.getY() > slopeHeight) {
						e.setY(slopeHeight - 0.1f);
						e.getCollider().top = true;
					}

					breakOut = true;
				}
				break;
			case 4:
				// |-/ slope

				// TODO
				break;
			case 5:
				// \-| slope

				// TODO
				break;
			default:
				// Outside the map, or invalid tile.
				break;
			}

			if (breakOut) {
				break;
			}

		}
	}
}
