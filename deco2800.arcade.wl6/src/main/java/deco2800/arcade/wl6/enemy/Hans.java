package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Hans extends Enemy {

    // Difficulty 1 = 850 health
    // Difficulty 2 = 950 health
    // Difficulty 3 = 1050 health
    // Difficulty 4 = 1200 health
    private int STARTING_HEALTH = 850;

    public Hans(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(512);
        setPain(false);
        setDamage(0);
        this.setStateChangeTime(0.1f);
        
        initialiseFromEnemyData(d);
        
    }



}
