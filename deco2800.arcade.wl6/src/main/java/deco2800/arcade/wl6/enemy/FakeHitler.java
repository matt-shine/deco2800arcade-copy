package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class FakeHitler extends Enemy {

    // Difficulty 1 = 200 health
    // Difficulty 2 = 300 health
    // Difficulty 3 = 400 health
    // Difficulty 4 = 500 health
    private int STARTING_HEALTH = 25;

    public FakeHitler(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        if (d.pathingDir == null) {
            setState(STATES.STAND);
        }
        else {
            setState(STATES.PATH);
        }
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(true);

        setDamage(0);
        setTextureName(d.texture);
    }



}
