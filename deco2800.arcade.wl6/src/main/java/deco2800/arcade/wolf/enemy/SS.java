package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class SS extends Enemy {

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

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }

}
