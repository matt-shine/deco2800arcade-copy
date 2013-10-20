package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class FakeHitler extends Enemy {

    private final int STARTING_HEALTH_1 = 100;
    private final int STARTING_HEALTH_2 = 150;
    private final int STARTING_HEALTH_3 = 200;
    private final int STARTING_HEALTH_4 = 250;

    public FakeHitler(int uid, DoodadInfo d) {
        super(uid);

        setPain(false);
        setRepeatShootChance(0.95f);
        setStateChangeTime(0.1f);
        setDamage(2);
        
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
