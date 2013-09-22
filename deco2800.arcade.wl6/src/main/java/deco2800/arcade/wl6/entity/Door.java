package deco2800.arcade.wl6.entity;

import deco2800.arcade.wl6.Doodad;
import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Renderer;
import deco2800.arcade.wl6.WL6Meta;

public class Door extends Doodad {

	
	private boolean vertical = false;
	private float openness = 0;
	
	
	public Door(int uid, boolean vertical, WL6Meta.KEY_TYPE key) {
		super(uid);
		
		this.vertical = vertical;
		
	}
	
	@Override
	public void tick(GameModel g) {
		
		float speed = 1.5f * g.delta();
		if (g.getPlayer().getPos().dst(this.getPos()) < 1.5f) {
			openness = (float) Math.min(openness + speed, 1.0f);
		} else {
			openness = (float) Math.max(openness - speed, 0.0f);
		}
		
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
