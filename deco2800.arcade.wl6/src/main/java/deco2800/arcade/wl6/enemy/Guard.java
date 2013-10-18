package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.WL6Meta;

public class Guard extends Enemy {

    // All difficulties = 25 health
    private int STARTING_HEALTH = 25;

    public Guard(int uid, DoodadInfo d) {
        super(uid);
        setHealth(STARTING_HEALTH);

        setPathDir(d.pathingDir);
        this.setAngle(WL6Meta.dirToAngle(d.facingDir));
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

    @Override
    public void init(GameModel model) {
        super.init(model);

    }

}
