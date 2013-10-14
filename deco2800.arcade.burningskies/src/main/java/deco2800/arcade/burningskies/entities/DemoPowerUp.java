package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.entities.bullets.DemoPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class DemoPowerUp extends PowerUp {
	
	private PlayScreen screen;
	//Either have all PowerUps inherit a screen, or only the BulletPattern ones.
	public DemoPowerUp(PlayScreen screen, String iconPath) {
			super(iconPath);
			this.screen = screen;			
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		//Demo of these changes. These are set though and not timed at the moment.
		player.setBulletPattern(new DemoPattern(player, screen), true);
		player.setMaxSpeed(600f);//2000 is quite insane speed.
	}

}
