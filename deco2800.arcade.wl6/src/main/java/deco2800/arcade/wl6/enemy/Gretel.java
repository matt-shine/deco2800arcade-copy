package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Gretel extends Enemy {

    // Difficulty 1 = 850 health
    // Difficulty 2 = 950 health
    // Difficulty 3 = 1050 health
    // Difficulty 4 = 1200 health
    private final int STARTING_HEALTH = 850;

    public Gretel(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(false);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
