package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.WL6Meta;

public class Mutant extends Enemy {

    // Difficulty 1 = 45 health
    // Difficulty 2-4 = 55 health
    private final int STARTING_HEALTH = 45;

    public Mutant(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(true);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
