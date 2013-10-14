package deco2800.arcade.wl6.enemy;

public class Guard extends Enemy {

    private int STARTING_HEALTH = 25;

    public Guard(int uid) {
        super(uid);

        setHealth(STARTING_HEALTH);
        pathSpeed = 512;
        chaseSpeed = 1536;
        pain = true;
        points = 100;

        damage = 0;
        dType = HITSCAN;
    }



}
