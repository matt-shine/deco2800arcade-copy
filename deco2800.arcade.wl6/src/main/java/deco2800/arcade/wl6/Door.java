package deco2800.arcade.wl6;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;

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

        float speed = 1.5f * g.delta();
        if (g.getPlayer().getPos().dst(this.getPos()) < 1.5f &&
        		(key == null || g.getPlayer().hasKey(key))) {
            openness = (float) Math.min(openness + speed, 1.0f);
        } else {
            openness = (float) Math.max(openness - speed, 0.0f);
        }
        
        //update collisions
        g.getCollisionGrid().setSolidAt((int) this.getPos().x, (int) this.getPos().y, openness >= 0.0f ? 0 : 1);
        
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
