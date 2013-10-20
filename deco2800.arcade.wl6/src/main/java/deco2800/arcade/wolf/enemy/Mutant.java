package deco2800.arcade.wolf.enemy;

import deco2800.arcade.wolf.DoodadInfo;

public class Mutant extends Enemy {

    private final int STARTING_HEALTH_1 = 45;
    private final int STARTING_HEALTH_2 = 55;
    private final int STARTING_HEALTH_3 = 55;
    private final int STARTING_HEALTH_4 = 55;

    public Mutant(int uid, DoodadInfo d) {
        super(uid);

        setPain(true);
        setRepeatShootChance(0.7f);
        setStateChangeTime(1f);
        this.setShootChance(1);
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
