package deco2800.arcade.burningskies.entities;

public class HealthPowerUp extends PowerUp {

	public HealthPowerUp(String iconPath) {
		super(iconPath);
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		player.heal(30);
	}

}
