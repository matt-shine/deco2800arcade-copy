package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

/**
 * This code is confusing and I don't recommend anyone else read it.
 * @author Simon
 *
 */
public class SecretDoor extends Doodad {

    private float openness = 0;
    private Vector2 movementDirection = null;
    private boolean firstDraw = true;
    private Vector2 gridPosition = null;
    
    public SecretDoor(int uid) {
        super(uid);
    }

    @Override
    public void init(GameModel g) {
    	
    	gridPosition = new Vector2((float) Math.floor(this.getPos().x), (float) Math.floor(getPos().y));
    	g.getCollisionGrid().setSolidAt((int) this.gridPosition.x, (int) this.gridPosition.y, 0);
    	
    }
    
    
    
    
	@Override
    public void tick(GameModel g) {

        float speed = 0.8f * g.delta();
        if (movementDirection == null && g.getPlayer().getPos().dst(this.getPos()) < 1f) {

            int x = (int) Math.floor(getPos().x);
            int y = (int) Math.floor(getPos().y);
            Level m = g.getMap();

            if (hasTileAt(x + 1, y, m) && hasTileAt(x - 1, y, m)) {
                movementDirection = new Vector2(0, this.getPos().y - g.getPlayer().getPos().y);
            }
            if (hasTileAt(x, y + 1, m) && hasTileAt(x, y - 1, m)) {
                movementDirection = new Vector2(this.getPos().x - g.getPlayer().getPos().x, 0);
            }
            //TODO: handle cases with walls:
            //(x + 1, y + 1), (x - 1, y + 1), (x + 1, y - 1), (x - 1, y - 1)
        }
        if (movementDirection != null) {
            openness = (float) Math.min(openness + speed, 2.0f);
        }
        
        
        //update the collision grid
        if (movementDirection != null) {
        	Vector2 newGridPosition = new Vector2((float) Math.floor(this.getPos().x), (float) Math.floor(getPos().y));
        	newGridPosition.add(new Vector2(movementDirection).nor().mul(openness));
            if (!newGridPosition.equals(this.gridPosition)) {
            	g.getCollisionGrid().setSolidAt((int) this.gridPosition.x, (int) this.gridPosition.y, 0);
        		g.getCollisionGrid().setSolidAt((int) newGridPosition.x, (int) newGridPosition.y, 1);
        	}
        	gridPosition = newGridPosition;
        }
        

    }

    /**
     * returns true if there is a tile that blocks the secret door
     * @param x
     * @param y
     * @param m
     * @return
     */
    private boolean hasTileAt(int x, int y, Level m) {
        return WL6Meta.block(m.getTerrainAt(x, y)).texture != null;
    }

    @Override
    public void draw(Renderer r) {

        if (firstDraw) {

            this.setTextureName(
                    WL6Meta.block(
                            r.getGame().getMap().getTerrainAt(
                                    (int) Math.floor(getPos().x),
                                    (int) Math.floor(getPos().y)
                            )
                    ).texture
            );

            firstDraw = false;
        }

        float x = this.getPos().x;
        float y = this.getPos().y;

        Vector2 offset = new Vector2(0, 0);

        if (movementDirection != null) {
            offset = new Vector2(movementDirection).nor().mul(openness);
        }

        r.drawBasicSprite(getTextureName(), x + offset.x, y + offset.y + 0.5f, 0);
        r.drawBasicSprite(getTextureName(), x + offset.x, y + offset.y - 0.5f, 0);
        r.drawBasicSprite(getTextureName(), x + offset.x + 0.5f, y + offset.y, 90);
        r.drawBasicSprite(getTextureName(), x + offset.x - 0.5f, y + offset.y, 90);

    }
}
