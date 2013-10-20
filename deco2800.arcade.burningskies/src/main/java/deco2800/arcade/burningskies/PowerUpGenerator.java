package deco2800.arcade.burningskies;

import java.util.ArrayList;

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
	private int occurence = 0;
	private PowerUp currentPowerUp = null; 
	private ArrayList<PowerUp> powers = new ArrayList<PowerUp>();
	
	public PowerUpGenerator(PlayScreen screen) {
		this.screen = screen;
	}
	
	/**
	 * Creates a random powerup to be dropped on the screen.
	 * @ensure X != null || Y != null
	 */
	public void randomPowerUp(float X, float Y) {
		PowerUp toAdd;
		 powers.add(new GodPowerUp(X, Y));
		 powers.add(new HealthPowerUp(X, Y)); //Cannot avoid this as X,Y 
		 powers.add(new SpeedPowerUp(X, Y));  //positions always change.
		 powers.add(new UpgradePowerUp(X, Y));
		 powers.add(new PatternPowerUp(screen, X, Y));
		 double chance = Math.random();
		 if (chance < 0.10) {
			 toAdd = powers.get(0);
		 } else if (chance > 0.85) {
			 toAdd = powers.get(4);
		 } else {
			 toAdd = powers.get((int)Math.ceil(chance*(powers.size()-2)));
		 }
		 if (occurence >= 3) {
			 powers.remove(currentPowerUp);
			 toAdd = powers.get((int)Math.round(Math.random()*(powers.size()-1)));
			 this.occurence = 0;
		 }
		 if (currentPowerUp == null || toAdd.getClass() != currentPowerUp.getClass()) {
			 currentPowerUp = toAdd;
			 this.occurence = 1;
		 } else {
			 this.occurence++;
		 }
		 screen.addPowerup(toAdd); 
	}
}
