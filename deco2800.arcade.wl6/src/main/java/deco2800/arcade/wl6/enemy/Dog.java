package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;

public class Dog extends Enemy {

    // All difficulties = 1 health
    private int STARTING_HEALTH = 1;

    public Dog(int uid, DoodadInfo d) {
        super(uid);

        setHealth(STARTING_HEALTH);
        setFaceDir(d.direction);
        setPathing(true);
        setState(STATES.PATH);
        setPathSpeed(1500);
        setChaseSpeed(1536);
        setPain(false);
        setDamage(0);

        setTextureName(d.texture);
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
