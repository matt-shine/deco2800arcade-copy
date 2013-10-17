package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Mob extends Doodad {

    private float angle = 0;
    private Vector2 vel = new Vector2();
    private Random rand;
    private int health;

    public Mob(int uid) {
        super(uid);
        rand = new Random();
    }

    @Override
    public void tick(GameModel gameModel) {
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            this.health = 0;
            die();
        }
        else {
            this.health = health;
        }
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public void die() {

    }


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean canSee(Doodad d, GameModel model) {
        Vector2 selfPos = this.getPos();
        Vector2 targetPos = d.getPos();
        Line2D line = new Line2D.Double(selfPos.x, selfPos.y, targetPos.x, targetPos.y);
        float dist = selfPos.dst(targetPos);

        if (dist > 15) {
            return false;
        }

        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {
                if (WL6Meta.block(model.getMap().getTerrainAt(i, j)).solid) {
                    Rectangle2D rect = new Rectangle2D.Double(i, j, 1, 1);
                    if (rect.intersectsLine(line)) {
                        return false;
                    }
                }
            }
        }

        return true;
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
    
    
    public Random getRand() {
    	return rand;
    }

}
