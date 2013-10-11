package deco2800.arcade.wl6.enemy;

public class Guard extends Enemy {

    public Guard(int uid) {
        super(uid);

        maxHealth = 25;
        health = maxHealth;
        pathSpeed = 512;
        chaseSpeed = 1536;
        pain = true;
        points = 100;

        damage = 0;
        damage = HITSCAN;
    }



}
