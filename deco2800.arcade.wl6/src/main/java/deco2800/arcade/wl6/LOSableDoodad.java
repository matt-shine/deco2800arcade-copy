package deco2800.arcade.wl6;

public class LOSableDoodad extends Doodad {
	
	private float angle = 0;
	
	
	public LOSableDoodad() {
		super();
	}
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void canSee(Doodad d) {
		//TODO
	}
	
}
