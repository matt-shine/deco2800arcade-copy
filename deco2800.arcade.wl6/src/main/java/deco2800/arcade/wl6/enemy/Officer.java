package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Officer extends Enemy {

    // All difficulties = 50 health
    private int STARTING_HEALTH = 50;

    public Officer(int uid, DoodadInfo d) {
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
