package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class SS extends Enemy {

    // All difficulties = 100 health
    private final int STARTING_HEALTH = 100;

    public SS(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(512);
        setPain(true);
        setDamage(0);
        this.setStateChangeTime(0.33f);
        
        initialiseFromEnemyData(d);
        
    }



}
