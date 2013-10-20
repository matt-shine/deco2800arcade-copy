package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class Guard extends Enemy {

    private final int STARTING_HEALTH = 25;

    public Guard(int uid, DoodadInfo d) {
        super(uid);

        setPain(true);
        
        //use default settings

        initialiseFromEnemyData(d);
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }
}
