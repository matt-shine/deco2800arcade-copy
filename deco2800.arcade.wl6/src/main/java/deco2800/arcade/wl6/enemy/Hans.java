package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Hans extends Enemy {

    private final int STARTING_HEALTH_1 = 850;
    private final int STARTING_HEALTH_2 = 950;
    private final int STARTING_HEALTH_3 = 1050;
    private final int STARTING_HEALTH_4 = 1200;

    public Hans(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(false);
        setDamage(0);
        this.setStateChangeTime(0.1f);
        
        initialiseFromEnemyData(d);
        
    }

    @Override
    public int getStartingHealth(int difficulty) {
        switch (difficulty) {
            case 1:
                return STARTING_HEALTH_1;
            case 2:
                return STARTING_HEALTH_2;
            case 3:
                return STARTING_HEALTH_3;
            case 4:
                return STARTING_HEALTH_4;
        }
        // Should never get here
        return STARTING_HEALTH_1;
    }

}
