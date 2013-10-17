package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Guard extends Enemy {

    private int STARTING_HEALTH = 25;

    public Guard(int uid, DoodadInfo d) {
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
