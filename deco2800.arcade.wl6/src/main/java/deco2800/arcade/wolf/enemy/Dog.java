package deco2800.arcade.wolf.enemy;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.GameModel;
import deco2800.arcade.wolf.Player;
import deco2800.arcade.wolf.Projectile;

public class Dog extends Enemy {

    private final int STARTING_HEALTH = 1;

    public Dog(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(1500);
        setPain(false);

        initialiseFromEnemyData(d);
        
    }

    @Override
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, getRand()) < 180 && dist == 1) {
            hit = true;
        }
        
        int damage = randInt(0, 255, getRand());

        if (hit) {
            damage = damage / 16;
        }
        else {
            damage = 0;
        }
	
        return damage;
    }
    
    
    @Override
    public void shootAtPlayer(GameModel g) {
    	
    	Player p = g.getPlayer();
        float dist = p.getPos().dst(this.getPos());
        int damage = calcDamage((int)dist, p.getVel() != new Vector2(0,0), true);
    	if (dist < 1) {
        	Projectile bullet = new Projectile(0, damage, true, "blank");
        	g.addDoodad(bullet);
        	bullet.setPos(this.getPos());
        	bullet.setVel(p.getPos().sub(bullet.getPos()).nor().mul(0.2f));
    	}
    	
    	
    	
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }
    
}
