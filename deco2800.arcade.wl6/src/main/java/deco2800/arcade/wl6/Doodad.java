package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

public class Doodad {

	
	private Vector2 pos = new Vector2(0, 0);
	private String textureName = "unknown";
	
	
	public Doodad() {
		
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
		//TODO bilboard
		renderer.drawBasicSprite(this.textureName, this.pos.x, this.pos.y);
	}
	
	
	public void tick(GameModel m) {
		
	}
	
	
	
}
