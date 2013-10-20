package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.WL6Meta;

public class Officer extends Enemy {

    private final int STARTING_HEALTH = 50;

    public Officer(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setSpeed(512);
        setPain(true);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        return STARTING_HEALTH;
    }

}
