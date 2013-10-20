package deco2800.arcade.wolf.enemy;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.GameModel;
import deco2800.arcade.wolf.Player;
import deco2800.arcade.wolf.Projectile;

public class Schabbs extends Enemy {

    private final int STARTING_HEALTH_1 = 850;
    private final int STARTING_HEALTH_2 = 950;
    private final int STARTING_HEALTH_3 = 1550;
    private final int STARTING_HEALTH_4 = 2400;

    public Schabbs(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(false);

        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        switch (difficulty) {
            case 1:
                return STARTING_HEALTH_1;
            case 2:
                return STARTING_HEALTH_2;
            case 3:
                return STARTING_HEALTH_3;
            case 4:
                return STARTING_HEALTH_4;
        }
        // Should never get here
        return STARTING_HEALTH_1;
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
