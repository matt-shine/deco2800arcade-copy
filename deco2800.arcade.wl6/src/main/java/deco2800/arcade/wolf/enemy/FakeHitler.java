package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class FakeHitler extends Enemy {

    private final int STARTING_HEALTH_1 = 200;
    private final int STARTING_HEALTH_2 = 300;
    private final int STARTING_HEALTH_3 = 400;
    private final int STARTING_HEALTH_4 = 500;

    public FakeHitler(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH_1);
        setSpeed(512);
        setPain(false);
        setDamage(0);

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
