package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Hitler extends Enemy {

    // Difficulty 1 = 800 health
    // Difficulty 2 = 950 health
    // Difficulty 3 = 1050 health
    // Difficulty 4 = 1200 health
    private int STARTING_HEALTH = 25;

    public Hitler(int uid, DoodadInfo d) {
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
