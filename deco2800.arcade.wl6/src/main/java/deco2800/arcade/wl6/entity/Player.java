package deco2800.arcade.wl6.entity;

import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Renderer;
import deco2800.arcade.wl6.entity.Mob;

public class Player extends Mob {

	public static final float SPEED = 3f;
	
	public Player(int uid) {
		super(uid);
	}
	
	
	public void draw(Renderer renderer) {
		if (renderer.isDebugMode()) {
			renderer.drawBasicSprite(getTextureName(), getPos().x, getPos().y, getAngle());
		}
		//no super call
	}
	
	@Override
	public void tick(GameModel model) {
		super.tick(model);
		//System.out.println("pos: " + this.getPos().x + " " + this.getPos().y + " " + this.getAngle());
	}
	
	
}
