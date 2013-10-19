package deco2800.arcade.wl6;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wl6.enemy.Enemy;

public class Projectile extends Mob {

	//damage to deal
	private int damage = 0;
	
	//is this projectile intended for enemies or the player
	private boolean evil = false;
	
	
	public Projectile(int uid, int damage, boolean evil, String texture) {
		super(uid);
		this.setTextureName(texture);
		this.evil = evil;
		this.damage = damage;
	}
	
	
	public void tick(GameModel g) {
		super.tick(g);
		
		//attempt to collide with all the doodads
		Iterator<Doodad> itr = g.getDoodadIterator();
        while (itr.hasNext()) {
			Doodad d = itr.next();
			if (d instanceof Mob) {
				if (d.getPos().dst(this.getPos()) < 0.4f) {

					if (evil && d instanceof Player) {
						              //important to set overheal to true because the 
						              //player will be capped to 100hp otherwise
						((Player) d).addHealth(-damage, true);
						g.destroyDoodad(this);
						break;
					}
					
					
					if (!evil && d instanceof Enemy) {
						if (!((Enemy) d).isDead()) {
							((Enemy) d).takeDamage(g, damage);
							g.destroyDoodad(this);
							break;
						}
					}
					
				}
			}
		}
        
		
		
	}
	
	
	@Override
	public void smoothMove(Vector2 avel, GameModel model) {
		
		//destroy self if hit a wall
		if (!move(model, avel)) {
			model.destroyDoodad(this);
		}
	}
	
	@Override
    public boolean isWalkableTile(GameModel model, int x, int y) {
    	return WL6Meta.block(model.getMap().getTerrainAt(x, y)).texture == null ||
    			((WL6Meta.hasDoorAt(x, y, model.getMap()) || WL6Meta.hasSecretDoorAt(x, y, model.getMap()) &&
    			model.getCollisionGrid().getSolidAt(x, y) == 0));
    }
    
    
	
	@Override
    public float getBoundingBoxSide() {
    	return 0.02f;
    }

}
