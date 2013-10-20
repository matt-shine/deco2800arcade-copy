package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class Fettgesicht extends Enemy {

    private final int STARTING_HEALTH_1 = 850;
    private final int STARTING_HEALTH_2 = 950;
    private final int STARTING_HEALTH_3 = 1550;
    private final int STARTING_HEALTH_4 = 1200;

    public Fettgesicht(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(false);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
