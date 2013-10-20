package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class SS extends Enemy {

    private final int STARTING_HEALTH = 45;

    public SS(int uid, DoodadInfo d) {
        super(uid);

        setPain(true);
        setRepeatShootChance(0.95f);
        setStateChangeTime(0.1f);
        setDamage(4);
        
        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }


}
