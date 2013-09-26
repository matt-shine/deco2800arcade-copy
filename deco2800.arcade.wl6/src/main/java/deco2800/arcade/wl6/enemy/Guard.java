package deco2800.arcade.wl6.enemy;

public class Guard extends Enemy {

    public Guard(int uid) {
        super(uid);

        state = STATES.STAND;
        totalHealth = 25;
        health = totalHealth;
        pathSpeed = 512;
        chaseSpeed = 1536;
        pain = true;
        points = 100;
    }

}
