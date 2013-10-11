package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.Mob;

import java.util.Random;

public class Enemy extends Mob {

    // Six possible states
    // An enemy starts in either Stand or Path states.  Enters chase state when player is spotted.
    // Once enemy has left stand or path states, it cannot reenter them.
    public enum STATES {
        NO_STATE,   // No state, error
        STAND,      // Idle state 1, motionless
        PATH,       // Idle state 2, following waypoints
        CHASE,      // Player spotted, chasing/dodging
        PAIN,       // Reacting to pain, interrupts any previous action/state
        ATTACK,     // Attacking the player
        DIE         // Death
    }

    // current state
    public STATES state = STATES.NO_STATE;
    // maximum health
    public int maxHealth;
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
    public int HITSCAN = 0;
    public int PROJECTILE = 1;
    public int dType;
    // random variable
    Random rand;

    public Enemy(int uid) {
        super(uid);
        rand = new Random();
    }

    // change states
    // delay is the time between animations (e.g. an officer transitions between chasing and attacking a lot faster then a guard)
    public void changeStates(STATES oldState, STATES newState, int delay) {

    }

    // follow patrol path (walk from waypoint to waypoint)
    public void walk(int nextWaypoint) {

    }

    // detect player


    // chase player


    // dodge (same as chase player, but with some randomized movement to 'dodge' players attacks)


    // attack player


    // react to pain


    // die


    /**
     * Damage Calculation
     *
     * @param dist Distance between enemy and player (in number of squares)
     * @param speed Is the player running or not
     * @param look Can the player see the shooter or not
     * @return Damage to be dealt.
     */
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, this.rand) < ((speed ? 160 : 256) - (dist * (look ? 16 : 8)))) {
            hit = true;
        }

        damage = randInt(0, 255, this.rand);

        if (hit) {
            if (dist < 2) {
                damage = damage / 4;
            }
            else if (dist >= 2 && dist < 4) {
                damage = damage / 8;
            }
            else if (dist >= 4) {
                damage = damage / 16;
            }
            else {
                damage = 0;
            }
        }
        else {
            damage = 0;
        }

        return damage;
    }

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value.
     * @param max Maximum value.  Must be greater than min.
     * @param rand Random value.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max, Random rand) {
        return rand.nextInt((max - min) + 1) + min;
    }
}

