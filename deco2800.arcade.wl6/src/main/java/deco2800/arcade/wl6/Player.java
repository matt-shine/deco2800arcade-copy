package deco2800.arcade.wl6;

public class Player extends LOSableDoodad {

	
	public Player() {
		super();
	}
	
	
	public void draw(Renderer renderer) {
		if (renderer.isDebugMode()) {
			super.draw(renderer);
		}
	}
	
	
	
	
}
