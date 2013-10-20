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

        setPain(false);
        setPersonalSpace(1);
        setRepeatShootChance(0.5f);
        
        initialiseFromEnemyData(d);
        
    }
    
    
    @Override
    public void tick(GameModel g) {
    	if (g.getPlayer().getPos().dst(this.getPos()) > 1.5) {
    		this.setShootChance(0);
    	} else {
    		this.setShootChance(1);
    	}
    	super.tick(g);
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
    public void dropItems(GameModel g) {
    	//do nothing
    }
    
    

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }
    
}
