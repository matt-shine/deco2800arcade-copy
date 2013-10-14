package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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
        if (health <= 0) {
            this.health = 0;
            die();
        }
        else {
            this.health = health;
        }
    }

    public void doDamage() {

    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }

    public void die() {

    }

    // FIXME: this currently stops secret doors from being opened
    public void setPos(Vector2 pos, Vector2 vel) {
        int checkX = vel.x > 0 ? (int) (pos.x + vel.x + 0.1) : (int) (pos.x + vel.y - 0.1);
        int checkY = vel.y > 0 ? (int) (pos.y + vel.y + 0.1) : (int) (pos.y + vel.y - 0.1);

        if (WL6Meta.hasSolidBlockAt(checkX, checkY, game.getMap())) {
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

    public boolean canSee(Doodad d) {
        Vector2 selfPos = this.getPos();
        Vector2 targetPos = d.getPos();
        Line2D line = new Line2D.Double(selfPos.x, selfPos.y, targetPos.x, targetPos.y);
        float dist = selfPos.dst(targetPos);
        //System.out.println(dist);
        /*if (dist > 10) {
            return false;
        }*/
        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {
                if (WL6Meta.block(game.getMap().getTerrainAt(i, j)).solid) {
                    Rectangle2D rect = new Rectangle2D.Double(i, j, i+1, j+1);
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

}
