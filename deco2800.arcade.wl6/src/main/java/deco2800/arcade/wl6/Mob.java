package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Mob extends Doodad {

    private float angle = 0;
    private Vector2 vel = new Vector2();
    protected GameModel game;
    protected Random rand;
    protected int health;

    public Mob(int uid) {
        super(uid);
        rand = new Random();
    }

    @Override
    public void tick(GameModel game) {
        this.game = game;
        setPos(getPos(), vel);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void doDamage() {

    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    // need to round the float either up or down depending on what direction we are approaching from
    // ie if vel is +ve, round down, if vel is -ve round up.
    public void setPos(Vector2 pos, Vector2 vel) {
        if (WL6Meta.hasSolidBlockAt((int) (pos.x + vel.x), (int) (pos.y + vel.y), game.getMap())) {
            // New position has a solid block or doodad.  Don't move position.
            setPos(pos);
        }
        else {
            setPos(pos.add(vel));
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void canSee(Doodad d) {
        //TODO
    }

    public Vector2 getVel() {
        return vel.cpy();
    }

    public void setVel(Vector2 vel) {
        this.vel = vel.cpy();
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
