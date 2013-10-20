package deco2800.arcade.burningskies;

import deco2800.arcade.burningskies.entities.GodPowerUp;
import deco2800.arcade.burningskies.entities.HealthPowerUp;
import deco2800.arcade.burningskies.entities.PatternPowerUp;
import deco2800.arcade.burningskies.entities.PowerUp;
import deco2800.arcade.burningskies.entities.SpeedPowerUp;
import deco2800.arcade.burningskies.entities.UpgradePowerUp;
import deco2800.arcade.burningskies.screen.PlayScreen;

//Class placement appropriate?
public class PowerUpGenerator {

	private PlayScreen screen;
	
	public PowerUpGenerator(PlayScreen screen) {
		this.screen = screen;
	}
	
	/**
	 * Creates a random powerup to be dropped on the screen.
	 * @ensure X != null || Y != null
	 */
	public void randomPowerUp(float X, float Y) {
		PowerUp toAdd;
		 double chance = Math.random();
		 if (chance < 0.10) { 						// 10% chance
			 toAdd = new GodPowerUp(X, Y);
		 } else if (chance < 0.20) { 				// 10% chance
			 toAdd = new PatternPowerUp(screen, X, Y);
		 } else if (chance < 0.40){					// 20% chance
			 toAdd = new HealthPowerUp(X, Y);
		 } else if (chance < 0.60) {				// 20% chance
			 toAdd = new SpeedPowerUp(X, Y);
		 } else {									// 40% chance
			 toAdd = new UpgradePowerUp(X, Y);
		 }
		 screen.addPowerup(toAdd); 
	}
}
