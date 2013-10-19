package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Renderer;

public class Doodad {


    private Vector2 pos = new Vector2(0, 0);
    private String textureName = null;
    private int uid = 0;

    // Holds this doodad's bounding rectangle
    //private final Rectangle bounds;  //Ignore for now, just testing

    public Doodad(int uid) {
        this.uid = uid;
        //bounds = new Rectangle();//Ignore for now, just testing
    }

    /**
     * Returns this <code>Doodad</code>'s bounding rectangle.
     * @return the bounding rectangle
     */
    //Ignore for now, just testing
    /*public Rectangle bounds() {
        bounds.x = getPos().x;
        bounds.y = getPos().y;
        bounds.width = 0.5f;
        bounds.height = 0.5f;
        return bounds;
    }*/
    
    public void init(GameModel g) {
    	
    }


    public int getUid() {
        return uid;
    }


    public Vector2 getPos() {
        return pos.cpy();
    }


    public void setPos(Vector2 pos) {
        this.pos = pos.cpy();
    }


    public String getTextureName() {
        return textureName;
    }


    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public void setX(float x) {
        pos = new Vector2(x, pos.y);
    }

    public void setY(float y) {
        pos = new Vector2(pos.x, y);
    }


    public void draw(Renderer renderer) {
        float angle = (float) Math.atan2(
                (float) (this.pos.x - renderer.getGame().getPlayer().getPos().x),
                (float) (this.pos.y - renderer.getGame().getPlayer().getPos().y)
        ) * (180.0f / (float)Math.PI) * -1;
        renderer.drawBasicSprite(this.textureName, this.pos.x, this.pos.y, angle);
    }


    public void tick(GameModel m) {

    }
    
    public Vector2 getBlockPos() {
    	return new Vector2((int) Math.floor(getPos().x), (int) Math.floor(getPos().y));
    }
    
    
}
