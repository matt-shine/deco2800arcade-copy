package deco2800.arcade.burningskies.entities;

public class DemoPowerUp extends PowerUp {
	
	//Either have all PowerUps inherit a screen, or only the BulletPattern ones.
	public DemoPowerUp(String iconPath) {
			super(iconPath);		
	}
	
	@Override
	public void powerOn(PlayerShip player) {
		//Demo of these changes. These are set though and not timed at the moment.
		player.upgradeBullets();
		player.setMaxSpeed(600f);//2000 is quite insane speed.
	}

}
