package deco2800.arcade.wolf.enemy;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.GameModel;
import deco2800.arcade.wolf.Player;
import deco2800.arcade.wolf.Projectile;

public class SS extends Enemy {

    private final int STARTING_HEALTH = 100;

    public SS(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(512);
        setPain(true);
        this.setStateChangeTime(0.33f);
        
        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }

    @Override
    public void shootAtPlayer(GameModel g) {
        Player p = g.getPlayer();
        float dist = this.getPos().dst(p.getPos()) * 2/3;
        int damage = calcDamage((int)dist, p.getVel() != new Vector2(0,0), true);
        Projectile bullet = new Projectile(0, damage, true, "worm");
        g.addDoodad(bullet);
        bullet.setPos(this.getPos());
        bullet.setVel(p.getPos().sub(bullet.getPos()).nor().mul(0.2f));
    }

}
