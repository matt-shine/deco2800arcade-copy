package deco2800.arcade.wl6.enemy;

public class Dog extends Enemy {

    public Dog(int uid) {
        super(uid);

        state = STATES.PATH;
        totalHealth = 1;
        health = totalHealth;
        pathSpeed = 1500;
        chaseSpeed = 3000;
        pain = false;
        points = 200;
    }
}
