package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class SS extends Enemy {

    // All difficulties = 100 health
    private int STARTING_HEALTH = 100;

    public SS(int uid, DoodadInfo d) {
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

        this.setTextureName(d.texture);
    }



}
