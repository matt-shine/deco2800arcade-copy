package deco2800.arcade.wl6;

public class Player extends Mob {

	public static final float SPEED = 3f;
	
	public Player(int uid) {
		super(uid);
	}
	
	
	public void draw(Renderer renderer) {
		if (renderer.isDebugMode()) {
			renderer.drawBasicSprite(getTextureName(), getPos().x, getPos().y, -getAngle());
		}
		//no super call
	}
	
	@Override
	public void tick(GameModel model) {
		super.tick(model);
	}
	
	
}
