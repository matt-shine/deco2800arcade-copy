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


    // Checks to see if the mob is trying to go through an obstructing block
    // FIXME need a better/correct way to do this
    public void setPos(Vector2 pos, Vector2 vel) {
        int x = (int) (pos.x + vel.x * 2);
        int y = (int) (pos.y + vel.y * 2);
        if (WL6Meta.hasObscuringBlockAt(x, y, game.getMap()) &&
                !WL6Meta.hasDoorAt(x, y, game.getMap())) {
            setPos(new Vector2(pos.x, pos.y));
        } else {
            setPos(new Vector2(pos.x + vel.x, pos.y + vel.y));
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
