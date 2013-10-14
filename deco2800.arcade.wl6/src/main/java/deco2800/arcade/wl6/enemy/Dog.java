package deco2800.arcade.wl6.enemy;

public class Dog extends Enemy {

    private int STARTING_HEALTH = 1;

    public Dog(int uid) {
        super(uid);

        state = STATES.PATH;
        setHealth(STARTING_HEALTH);
        pathSpeed = 1500;
        chaseSpeed = 3000;
        pain = false;
        points = 200;
    }

    @Override
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, this.rand) < 180) {
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
