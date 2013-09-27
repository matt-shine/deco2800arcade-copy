package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.Mob;

public class Enemy extends Mob {

    // Six possible states
    public enum STATES {
        STAND,
        PATH,
        CHASE,
        PAIN,
        ATTACK,
        DIE
    }

    // current state
    public STATES state;
    // total health
    public int totalHealth;
    // current health
    public int health;
    // speed while pathing
    public int pathSpeed;
    // speed while chasing
    public int chaseSpeed;
    // suffers from pain (they have an animation that they do nothing in when they get hit, interrupts their current action)
    public boolean pain;
    // points awarded when killed
    public int points;
    // damage
    public int damage;
    // damage type (hitscan, projectile)

    // damage range

    // damage accuracy (ss, officers are more accurate then guards)

    // something to store the time between animations (e.g. an officer transitions between chasing and attacking a lot faster then a guard)


    public Enemy(int uid) {
        super(uid);
    }
}

