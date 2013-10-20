package deco2800.arcade.wolf.enemy;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.GameModel;
import deco2800.arcade.wolf.Player;
import deco2800.arcade.wolf.Projectile;

public class Gretel extends Enemy {

    private final int STARTING_HEALTH_1 = 850;
    private final int STARTING_HEALTH_2 = 950;
    private final int STARTING_HEALTH_3 = 1050;
    private final int STARTING_HEALTH_4 = 1200;

    public Gretel(int uid, DoodadInfo d) {
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
