package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.GameModel;

public class Dog extends Enemy {

    // All difficulties = 1 health
    private int STARTING_HEALTH = 1;

    public Dog(int uid, DoodadInfo d) {
        super(uid);

        setState(STATES.PATH);
        setHealth(STARTING_HEALTH);
        this.setTextureName(d.texture);
    }

    @Override
    public void tick(GameModel gameModel) {
        super.tick(gameModel);

    }


    @Override
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, getRand()) < 180 && dist == 1) {
            hit = true;
        }
        
        setDamage(randInt(0, 255, getRand()));

        if (hit) {
            setDamage(getDamage() / 16);
        }
        else {
            setDamage(0);
        }
	
        return getDamage();
    }
}
