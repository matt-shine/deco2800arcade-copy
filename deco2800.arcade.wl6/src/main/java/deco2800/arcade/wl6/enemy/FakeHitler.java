package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class FakeHitler extends Enemy {

    // Difficulty 1 = 200 health
    // Difficulty 2 = 300 health
    // Difficulty 3 = 400 health
    // Difficulty 4 = 500 health
    private int STARTING_HEALTH = 200;

    public FakeHitler(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(false);
        setDamage(0);

        initialiseFromEnemyData(d);
        
    }



}
