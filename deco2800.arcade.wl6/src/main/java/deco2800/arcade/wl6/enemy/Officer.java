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
        pathSpeed = 512;
        chaseSpeed = 1536;
        pain = true;

        damage = 0;
        setTextureName(d.texture);
    }



}
