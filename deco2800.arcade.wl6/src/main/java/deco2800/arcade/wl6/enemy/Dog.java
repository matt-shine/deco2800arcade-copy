package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.GameModel;

public class Dog extends Enemy {

    private int STARTING_HEALTH = 1;

    public Dog(int uid, DoodadInfo d) {
        super(uid);

        setState(STATES.PATH);
        setHealth(STARTING_HEALTH);
        pathSpeed = 1500;
        chaseSpeed = 3000;
        pain = false;
        this.setTextureName(d.texture);
    }

    @Override
    public void tick(GameModel gameModel) {
        super.tick(gameModel);

    }


    @Override
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, this.rand) < 180 && dist == 1) {
            hit = true;
        }

        damage = randInt(0, 255, this.rand);

        if (hit) {
            damage = damage / 16;
        }
        else {
            damage = 0;
        }

        return damage;
    }
}
