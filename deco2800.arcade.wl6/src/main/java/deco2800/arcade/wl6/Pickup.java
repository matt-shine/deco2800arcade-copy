package deco2800.arcade.wl6;

public class Pickup extends Doodad {

	@SuppressWarnings("unused")
	private int health = 0;
	@SuppressWarnings("unused")
	private int points = 0;
	@SuppressWarnings("unused")
	private int gun = 0;
	@SuppressWarnings("unused")
	private int ammo = 0;
	
	
	public Pickup(int uid, int health, int points, int ammo, int gun) {
		super(uid);
		this.health = health;
		this.points = points;
		this.gun = gun;
		this.ammo = ammo;
	}

	@Override
	public void tick(GameModel game) {
		
		if (this.getPos().dst(game.getPlayer().getPos()) < 0.8) {
			game.destroyDoodad(this);
		}
		
	}
	
	

}
