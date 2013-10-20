package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Player;
import deco2800.arcade.wl6.Projectile;

public class Dog extends Enemy {

    // All difficulties = 1 health
    private int STARTING_HEALTH = 1;

    public Dog(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setPathSpeed(1500);
        setChaseSpeed(1536);
        setPain(false);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }

    @Override
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, getRand()) < 180 && dist == 1) {
            hit = true;
        }
        
        setDamage(randInt(0, 255, getRand()));

        if (hit) {
            setDamage(getDamage() / 16);
        }
        else {
            setDamage(0);
        }
	
        return getDamage();
    }
    
    
    @Override
    public void shootAtPlayer(GameModel g) {
    	
    	Player p = g.getPlayer();
    	if (p.getPos().dst(this.getPos()) < 1) {
        	Projectile bullet = new Projectile(0, 10, true, "blank");
        	g.addDoodad(bullet);
        	bullet.setPos(this.getPos());
        	bullet.setVel(p.getPos().sub(bullet.getPos()).nor().mul(0.2f));
    	}
    	
    	
    	
    }
    
}
