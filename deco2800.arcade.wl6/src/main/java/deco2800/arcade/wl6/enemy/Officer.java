package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.WL6Meta;

public class Officer extends Enemy {

    // All difficulties = 50 health
    private int STARTING_HEALTH = 50;

    public Officer(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setFaceDir(d.direction);
        this.setAngle(WL6Meta.dirToAngle(d.direction));
        if (!d.pathing) {
            setPathing(false);
            instantStateChange(STATES.STAND);
        }
        else {
            setPathing(true);
            instantStateChange(STATES.PATH);
        }
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(true);
        setDamage(0);

        setTextureName(d.texture);
    }



}
