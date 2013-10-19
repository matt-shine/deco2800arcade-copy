package deco2800.arcade.wl6;

public class LevelEnd extends SecretDoor {
	
	
	public LevelEnd(int uid) {
		super(uid);
	}

	@Override
	public void tick(GameModel g) {
		//no call to super.tick
		
		if (g.getPlayer().getBlockPos().equals(this.getBlockPos())) {
			
			g.nextLevel();
			
		}
		
		
	}
	
	@Override
	public void draw(Renderer r) {
		//do nothing
	}
	
	
}
