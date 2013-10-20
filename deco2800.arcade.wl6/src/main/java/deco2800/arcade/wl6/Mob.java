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
    public void tick(GameModel model) {
    	smoothMove(new Vector2(getVel()).mul(model.delta() * 60), model);

    }

    
    public void smoothMove(Vector2 avel, GameModel model) {

        if (move(model, avel)) {
            //we just moved as intended
        } else if (move(model, new Vector2(avel.x, 0))) {
            //we just moved, but only in the x direction
        } else if (move(model, new Vector2(0, avel.y))) {
            //we just moved, but only in the y direction
        } else {
            //we are stuck
        }

    }
    
    
    /**
     * tries to move the object. returns true if successful
     * @param model
     * @param vec
     * @return
     */
    public boolean move(GameModel model, Vector2 vec) {

        Vector2 targetPos = this.getPos().add(vec);
        int x1 = (int) Math.floor(targetPos.x - getBoundingBoxSide() / 2);
        int y1 = (int) Math.floor(targetPos.y - getBoundingBoxSide() / 2);
        int x2 = (int) Math.floor(targetPos.x + getBoundingBoxSide() / 2);
        int y2 = (int) Math.floor(targetPos.y + getBoundingBoxSide() / 2);

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if (!isWalkableTile(model, x, y)) {
                    return false;
                }
            }
        }

        setPos(getPos().add(vec));
        return true;

    }

    
    public boolean isWalkableTile(GameModel model, int x, int y) {
    	return model.getCollisionGrid().getSolidAt(x, y) == 0;
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

    public void doDamage(GameModel gameModel) {

    }

    public void takeDamage(GameModel model, int damage) {
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

    // TODO take facing into account
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
    
    public float getBoundingBoxSide() {
    	return 0.4f;
    }

}
