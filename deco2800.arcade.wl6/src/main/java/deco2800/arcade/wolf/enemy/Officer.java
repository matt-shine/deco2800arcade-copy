package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class Officer extends Enemy {

    private final int STARTING_HEALTH = 75;

    public Officer(int uid, DoodadInfo d) {
        super(uid);

        setPain(true);
        setDamage(11);
        setStateChangeTime(0.1f);
        setRepeatShootChance(0);
        
        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }

}
