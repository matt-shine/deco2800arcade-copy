package deco2800.arcade.wl6;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;

public class Pickup extends Doodad {

    private int health = 0;
    private int points = 0;
    private int gun = 0;
    private int ammo = 0;
    private KEY_TYPE key = null;

    public Pickup(int uid, int health, int points, int ammo, int gun) {
        super(uid);
        this.health = health;
        this.points = points;
        this.gun = gun;
        this.ammo = ammo;
    }
    
    public Pickup(int uid, KEY_TYPE k) {
        super(uid);
        this.key = k;
    }

    @Override
    public void tick(GameModel game) {

    	Player p = game.getPlayer();
    	
        if (getPos().dst(p.getPos()) < 0.8) {
            boolean destroy = false;
            
            if (p.getAmmo() < 99 && ammo > 0) {
            	p.addAmmo(ammo);
            	destroy = true;
            }
            
            if (points > 0) {
            	p.addPoints(points);
            	destroy = true;
            }
            
            if (this.gun != 0) {
            	p.addGun(gun);
            	destroy = true;
            }
            
            if (p.getHealth() < 100 || points > 0) {
            	p.addHealth(health, points != 0);
            	destroy = true;
            }
            
            if (key != null) {
            	p.addKey(key);
            	destroy = true;
            }
            
            if (destroy) {
            	game.destroyDoodad(this);
            }
            
        }

    }



}
