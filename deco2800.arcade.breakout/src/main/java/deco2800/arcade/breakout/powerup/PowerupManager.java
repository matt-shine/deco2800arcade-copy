package deco2800.arcade.breakout.powerup;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.breakout.GameScreen;

public class PowerupManager {

	private ArrayList<Powerup> powerups;
	
	public PowerupManager(GameScreen gs) {
		powerups = new ArrayList<Powerup>();
		powerups.add(new LifePowerup(gs));
		powerups.add(new SlowBall(gs));
		powerups.add(new IncreasePaddle(gs));
		powerups.add(new DecreasePaddle(gs));
		powerups.add(new IncreaseBallNo(gs));
	}
	
	public void handlePowerup(float x, float y) {
		Powerup p = dropPowerup();
		p.setPos(x, y);
	}
	
	public void moveAll() {
		for (Powerup p : powerups) {
			if (p.getNumActive() > 0) {
				p.move();
			}
		}
	}
	
	public void renderAll(SpriteBatch batch) {
		for (Powerup p : powerups) {
			if (p.getNumActive() > 0) {
				p.render(batch, p.getSprite());
			}
		}
	}
	
	private Powerup dropPowerup() {
		Random randomNum = new Random();
		int index = randomNum.nextInt(5);
		powerups.get(index).setNumActive(1);
		return powerups.get(index);
	}
	
	public void checkCollision(Rectangle paddle) {
		for (Powerup p : powerups) {
			if (p.getNumActive() > 0) {
				if (p.getBounds().overlaps(paddle)) {
					p.applyPowerup();
					p.setNumActive(-1);
				}
			}
		}
	}
	
	public void checkBelowScreen() {
		for (Powerup p : powerups) {
			if (p.getNumActive() > 0) {
				if ((p.getBounds().y + p.getBounds().height) < 0) {
					p.setNumActive(-1);
				}
			}
		}
	}
	
	public void dispose() {
		for (Powerup p : powerups) {
			while (p.getNumActive() > 0) {
				p.setNumActive(-1);
			}
		}
	}
}
