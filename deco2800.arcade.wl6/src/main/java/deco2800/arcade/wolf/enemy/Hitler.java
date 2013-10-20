package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class Hitler extends Enemy {

    private final int STARTING_HEALTH_1 = 800;
    private final int STARTING_HEALTH_2 = 950;
    private final int STARTING_HEALTH_3 = 1050;
    private final int STARTING_HEALTH_4 = 1200;

    public Hitler(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(false);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
