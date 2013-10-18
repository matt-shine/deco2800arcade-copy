package deco2800.arcade.wl6.enemy;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Mob;
import deco2800.arcade.wl6.WL6Meta;

import java.util.LinkedList;

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
    private STATES state = STATES.NO_STATE;
    //
    protected float pathSpeed;
    //
    protected float chaseSpeed;
    //
    protected Vector2 faceDir = new Vector2();


    // path list
    protected LinkedList<Vector2> path;
    // suffers from pain (they have an animation that they do nothing in when they get hit, interrupts their current action)
    protected boolean pain;
    // damage
    protected int damage;


    public Enemy(int uid) {
        super(uid);
    }

    @Override
    public void tick(GameModel gameModel) {
        super.tick(gameModel);
        detectPlayer(gameModel);
        if (this.getHealth() <= 0) {
            changeStates(STATES.DIE, 0);
            gameModel.destroyDoodad(this);
        }
    }

    public void setState(STATES state) {
        this.state = state;
    }

    /**
     * Tells the enemy to change states
     * @param State state to change the enemy to
     * @param delay time taken to change states
     *              (e.g. An officer takes less time to go CHASE -> ATTACK then a guard)
     */
    public void changeStates(STATES State, int delay) {
        setState(State);
    }

    // follow patrol path
    public void path() {

    }

    // detect player
    public void detectPlayer(GameModel gameModel) {
        if (canSee(gameModel.getPlayer(), gameModel)) {
            changeStates(STATES.ATTACK, 0);
            doDamage(gameModel);
            changeStates(STATES.CHASE, 0);
        }
    }

    // chase player


    // dodge (same as chase player, but with some randomized movement to 'dodge' players attacks)


    // attack player


    // react to pain


    @Override
    public void takeDamage(GameModel model, int damage) {
        int d = damage;
        if (state == STATES.STAND || state == STATES.PATH) {
            d = d * 2;
        }
        if (pain) {
            changeStates(STATES.PAIN, 0);
            setHealth(getHealth() - d);
            changeStates(STATES.CHASE, 0);
        }
        else {
            setHealth(getHealth() - d);
        }
    }

    @Override
    public void doDamage(GameModel gameModel) {
        float dist = this.getPos().dst(gameModel.getPlayer().getPos());
        boolean speed = true;
        boolean look = true;
        int damage = calcDamage((int)dist, speed, look);
        gameModel.getPlayer().takeDamage(gameModel, damage);
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
        if (randInt(0, 255, getRand()) < ((speed ? 160 : 256) - (dist * (look ? 16 : 8)))) {
            hit = true;
        }

        damage = randInt(0, 255, getRand());

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

    // TODO Ugly mess that I need to change.  Working on it atm
    public void calculatePath(GameModel gameModel) {
        path = new LinkedList<Vector2>();
        WL6Meta.DIRS[][] waypoints = gameModel.getWapoints();

        int x = (int)getPos().x;
        int y = (int)getPos().y;
        int angle = (int)this.getAngle();
        path.addFirst(new Vector2(x, y));

        boolean complete = false;

        while (!complete) {
            switch (angle) {
                case 0:
                    x = x + 1;
                    y = y + 0;
                    break;
                case 45:
                    x = x + 1;
                    y = y + 1;
                    break;
                case 90:
                    x = x + 0;
                    y = y + 1;
                    break;
                case 135:
                    x = x - 1;
                    y = y + 1;
                    break;
                case 180:
                    x = x - 1;
                    y = y + 0;
                    break;
                case 225:
                    x = x - 1;
                    y = y - 1;
                    break;
                case 270:
                    x = x + 0;
                    y = y - 1;
                    break;
                case 315:
                    x = x + 1;
                    y = y - 1;
                    break;
            }

            if (waypoints[x][y] != null) {
                if(path.contains(new Vector2(x, y))) {
                    complete = true;
                }
                else {
                    path.add(new Vector2(x, y));
                    angle = (int)WL6Meta.dirToAngle(waypoints[x][y]);
                }
            }
        }
    }
}

