package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Mutant extends Enemy {

    // Difficulty 1 = 45 health
    // Difficulty 2-4 = 55 health
    private int STARTING_HEALTH = 25;

    public Mutant(int uid, DoodadInfo d) {
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
