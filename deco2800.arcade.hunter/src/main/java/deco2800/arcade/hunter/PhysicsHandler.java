package deco2800.arcade.hunter;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.ForegroundLayer;
import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;

public class PhysicsHandler {
	public static void checkCollisions(EntityCollection ec) {
		
	}
	
	public static void checkMapCollisions(EntityCollection entities, ForegroundLayer foregroundLayer) {
		//Check map collisions for every entity that can collide with the map (should be player, animals & projectiles)
		
		//Move the item so that it no longer intersects with the map
		
		for (Entity e : entities) {
			e.getCollider().clear();
			
			//Bottom edge
			checkMapCollisionBottom(e, foregroundLayer);
			
			//Right edge			
			checkMapCollisionRight(e, foregroundLayer);
			
			//Top edge
			checkMapCollisionTop(e, foregroundLayer);
		}
	}
	
	private static void checkMapCollisionBottom(Entity e, ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		boolean breakOut = false;

		for (int i = (int) right; i >= (int) e.getX(); i -= Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt(i, e.getY());
			float slopeHeight = 0;
			
			switch (tile) {
				case 0:
					//Air tile
					break;
				case 1:
					//Solid tile, do something
					e.getCollider().bottom = true;
					e.setY((float) (Math.ceil(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE) + 0.5f);
					breakOut = true;
					break;
				case 2:
					//   /_|  slope
					if (i == (int) right) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
						if (e.getY() < slopeHeight) {
							e.setY(slopeHeight + 0.5f);
							e.getCollider().bottom = true;
						}
						breakOut = true;
					}
					break;
				case 3:
					//   |_\ slope
					if (i <= (int) e.getX()) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e.getX() % Config.TILE_SIZE));
						if (e.getY() < slopeHeight) {
							e.setY(slopeHeight + 0.1f);
							e.getCollider().bottom = true;
						}
						
						breakOut = true;
					}
					break;
				case 4:
					//   |-/ slope
					
					//TODO
					break;
				case 5:
					//   \-| slope
					
					//TODO
					break;
				default:
					//Outside the map, or invalid tile.
					break;
			}
			
			if (breakOut) {
				break;
			}
			
		}
	}
	
	private static void checkMapCollisionTop(Entity e, ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		float top = e.getY() + e.getHeight();
		
		boolean breakOut = false;

		for (int i = (int) right; i >= (int) e.getX(); i -= Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt(i, top);
			float slopeHeight = 0;
			switch (tile) {
				case 0:
					//Air tile
					break;
				case 1:
					//Solid tile, do something
					e.getCollider().top = true;
					e.setY((float) (Math.ceil((e.getY() - Config.TILE_SIZE) / Config.TILE_SIZE) * Config.TILE_SIZE) - 0.5f);
					breakOut = true;
					break;
				case 2:
					//   /_|  slope
					if (i == (int) right) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
						if (e.getY() > slopeHeight) {
							e.setY(slopeHeight - 0.5f);
							e.getCollider().top = true;
						}
						breakOut = true;
					}
					break;
				case 3:
					//   |_\ slope
					if (i <= (int) e.getX()) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e.getX() % Config.TILE_SIZE));
						if (e.getY() > slopeHeight) {
							e.setY(slopeHeight - 0.1f);
							e.getCollider().top = true;
						}
						
						breakOut = true;
					}
					break;
				case 4:
					//   |-/ slope
					
					//TODO
					break;
				case 5:
					//   \-| slope
					
					//TODO
					break;
				default:
					//Outside the map, or invalid tile.
					break;
			}
			
			if (breakOut) {
				break;
			}
			
		}
	}
	
	private static void checkMapCollisionRight(Entity e, ForegroundLayer foregroundLayer) {
		float right = e.getX() + e.getWidth();
		float top = e.getY() + e.getHeight();
		
		boolean breakOut = false;

		for (int i = (int) e.getY(); i <= top; i += Config.TILE_SIZE) {
			int tile = foregroundLayer.getCollisionTileAt((int) right, i);
			float slopeHeight = 0;
			switch (tile) {
				case 0:
					//Air tile
					break;
				case 1:
					//Solid tile, do something
					e.getCollider().right = true;
					e.setX((float) (Math.ceil((e.getX() - Config.TILE_SIZE) / Config.TILE_SIZE) * Config.TILE_SIZE) - 0.5f);
					breakOut = true;
					break;
				case 2:
					//   /_|  slope
					if (i == (int) right) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
						if (e.getY() > slopeHeight) {
							e.setY(slopeHeight - 0.5f);
							e.getCollider().top = true;
						}
						breakOut = true;
					}
					break;
				case 3:
					//   |_\ slope
					if (i <= (int) e.getX()) {
						slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e.getX() % Config.TILE_SIZE));
						if (e.getY() > slopeHeight) {
							e.setY(slopeHeight - 0.1f);
							e.getCollider().top = true;
						}
						
						breakOut = true;
					}
					break;
				case 4:
					//   |-/ slope
					
					//TODO
					break;
				case 5:
					//   \-| slope
					
					//TODO
					break;
				default:
					//Outside the map, or invalid tile.
					break;
			}
			
			if (breakOut) {
				break;
			}
			
		}
	}
}
