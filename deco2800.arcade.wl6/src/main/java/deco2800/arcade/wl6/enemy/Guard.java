package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Guard extends Enemy {

    // All difficulties = 25 health
    private final int STARTING_HEALTH = 25;

    public Guard(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(true);
        setDamage(0);
        
        initialiseFromEnemyData(d);
        
    }
}
