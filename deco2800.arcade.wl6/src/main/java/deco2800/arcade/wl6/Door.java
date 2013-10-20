package deco2800.arcade.wl6;

import java.util.Iterator;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;
import deco2800.arcade.wl6.enemy.Enemy;

public class Door extends Doodad {


    private boolean vertical = false;
    private float openness = 0;
    private KEY_TYPE key = null;

    public Door(int uid, boolean vertical, WL6Meta.KEY_TYPE key) {
        super(uid);

        this.vertical = vertical;
        this.key = key;
    }

    @Override
    public void tick(GameModel g) {

    	boolean shouldOpenEnemy = false;
    	boolean shouldOpenPlayer = false;
    	
        Iterator<Doodad> itr = g.getDoodadIterator();
        while (itr.hasNext()) {
			Doodad d = itr.next();
			if (d instanceof Mob) {
				if (d.getPos().dst(this.getPos()) < 1.5f) {
					shouldOpenEnemy = shouldOpenEnemy || (d instanceof Enemy);
					shouldOpenPlayer = shouldOpenPlayer || (d instanceof Player);
				}
			}
		}
        
    	if (key != null) {
    		shouldOpenEnemy = false;
    		
    		if (!g.getPlayer().hasKey(key)) {
    			shouldOpenPlayer = false;
    		}
    		
    	}
        
    	float speed = 1.5f * g.delta();
        if (shouldOpenEnemy || shouldOpenPlayer) {
            openness = Math.min(openness + speed, 1.0f);
        } else {
            openness = Math.max(openness - speed, 0.0f);
        }
        
        //update collisions
        g.getCollisionGrid().setSolidAt((int) this.getPos().x, (int) this.getPos().y, openness >= 0.8f ? 0 : 1);
        
        

        
    }


    @Override
    public void draw(Renderer r) {

        float x = this.getPos().x;
        float y = this.getPos().y;
        float angle = vertical ? 0 : 90;
        float offX = vertical ? 0.5f : 0;
        float offY = vertical ? 0 : 0.5f;

        r.drawBasicSprite(
                "door",
                x + (vertical ? openness : 0),
                y + (vertical ? 0 : openness),
                angle
        );
        r.drawBasicSprite("door", x - offX, y - offY, angle + 90);
        r.drawBasicSprite("door", x + offX, y + offY, angle - 90);


    }



}
