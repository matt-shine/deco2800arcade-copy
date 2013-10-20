package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.WL6Meta;

public class Mutant extends Enemy {

    private final int STARTING_HEALTH_1 = 45;
    private final int STARTING_HEALTH_2 = 55;
    private final int STARTING_HEALTH_3 = 55;
    private final int STARTING_HEALTH_4 = 55;

    public Mutant(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(true);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
