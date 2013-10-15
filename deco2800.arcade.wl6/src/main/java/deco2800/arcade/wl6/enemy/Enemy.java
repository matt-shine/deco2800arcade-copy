package deco2800.arcade.wl6.enemy;

import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Mob;

public class Enemy extends Mob {

    /**
     * Six possible states
     * An enemy starts in either Stand or Path states.  Enters chase state when player is spotted or gunfire is heard.
     * Once enemy has left the stand or path states, it cannot reenter them.
     */
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


    public Enemy(int uid) {
        super(uid);
    }

    @Override
    public void tick(GameModel game) {
        super.tick(game);
        detectPlayer();
    }

    public STATES getState() {
        return state;
    }

    public void setState(STATES state) {
        this.state = state;
    }

    private void calculatePath() {
        float initX = this.getPos().x;
        float initY = this.getPos().y;
        int initAngle = (int)this.getAngle();

        float x;
        float y;
        int angle;

        switch (initAngle){
            case 0:
                x = initX;
                y = initY + 1;
                angle = initAngle;

            case 45:

            case 90:
        }
    }

    /**
     * Tells the enemy to change states
     * @param oldState state the enemy is currently in
     * @param newState state to change the enemy to
     * @param delay time taken to change states
     *              (e.g. An officer takes less time to go CHASE -> ATTACK then a guard)
     */
    public void changeStates(STATES oldState, STATES newState, int delay) {
        setState(newState);
    }

    // follow patrol path (walk from waypoint to waypoint)
    public void walk(int nextWaypoint) {

    }

    // detect player
    public void detectPlayer() {
        if (canSee(game.getPlayer())) {
            setState(STATES.ATTACK);
            doDamage();
        }
    }

    // chase player


    // dodge (same as chase player, but with some randomized movement to 'dodge' players attacks)


    // attack player


    // react to pain


    @Override
    public void doDamage() {
        float dist = this.getPos().dst(game.getPlayer().getPos());
        boolean speed = false;
        boolean look = false;
        int damage = calcDamage((int)dist, speed, look);
        game.getPlayer().takeDamage(damage);
    }

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
        if (randInt(0, 255, rand) < ((speed ? 160 : 256) - (dist * (look ? 16 : 8)))) {
            hit = true;
        }

        damage = randInt(0, 255, rand);

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
}

