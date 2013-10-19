package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.WL6Meta;

public class Guard extends Enemy {

    // All difficulties = 25 health
    private int STARTING_HEALTH = 25;

    public Guard(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setFaceDir(d.direction);
        this.setAngle(WL6Meta.dirToAngle(d.direction));
        if (!d.pathing) {
            setPathing(false);
            addInstantStateChange(STATES.STAND);
        }
        else {
            setPathing(true);
            addInstantStateChange(STATES.PATH);
        }
        setPathSpeed(512);
        setChaseSpeed(1536);
        setPain(true);
        setDamage(0);
        
        setTextureName(d.texture);
    }
}
