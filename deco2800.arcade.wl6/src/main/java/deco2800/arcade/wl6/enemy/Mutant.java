package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.WL6Meta;

public class Mutant extends Enemy {

    // Difficulty 1 = 45 health
    // Difficulty 2-4 = 55 health
    private int STARTING_HEALTH = 45;

    public Mutant(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(512);
        setPain(true);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
