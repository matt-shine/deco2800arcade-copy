package deco2800.arcade.breakout.powerup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.breakout.GameScreen;

public class PowerupManager {

	// Record the number of active of each powerup
	private ArrayList<Integer> numPowerups;
	private ArrayList<Powerup> powerups;
	
	private GameScreen gs;
	
	public PowerupManager(GameScreen gs) {
		this.gs = gs;
		powerups = new ArrayList<Powerup>();
		numPowerups = new ArrayList<Integer>();
		// 0 is LifePowerup
		numPowerups.add(0);
		// 1 is SlowBall
		numPowerups.add(0);
		// 2 is IncreasePaddle
		numPowerups.add(0);
		// 3 is DecreasePaddle
		numPowerups.add(0);
		// 4 is IncreaseBallNo
		numPowerups.add(0);
	}
	
	public void handlePowerup(float x, float y) {
		Powerup p = dropPowerup();
		powerups.add(p);
		p.setPos(x, y);
	}
	
	public void moveAll() {
		for (Powerup p : powerups) {
			p.move();
		}
	}
	
	public void renderAll(SpriteBatch batch) {
		for (Powerup p : powerups) {
			p.render(batch, p.getSprite());
		}
	}
	
	private Powerup dropPowerup() {
		Random randomNum = new Random();
		int index = randomNum.nextInt(5);
		return determinePowerup(index);
	}
	
	private Powerup determinePowerup (int index) {
		// increase the number of active powerups of powerup at 'index' of powerups array
		increaseNumPowerups(index);
		switch (index) {
		case 0:
			return new LifePowerup(gs);
		case 1:
			return new SlowBall(gs);
		case 2:
			return new IncreasePaddle(gs);
		case 3:
			return new DecreasePaddle(gs);
		default:
			return new IncreaseBallNo(gs);
		}
		
	}
	
	private void increaseNumPowerups(int index) {
		numPowerups.set(index, numPowerups.get(index) + 1);
	}
	
	private void decreaseNumPowerups(int index) {
		numPowerups.set(index, numPowerups.get(index) - 1);
	}
	
	private int determineIndex(Powerup p) {
		if (p instanceof LifePowerup) {
			return 0;
		} else if (p instanceof SlowBall) {
			return 1;
		} else if (p instanceof IncreasePaddle) {
			return 2;
		} else if (p instanceof DecreasePaddle) {
			return 3;
		}
		return 4;
	}
	
	public void checkCollision(Rectangle paddle) {
		// Loop with iterator to avoid ConcurrentModificationException
		for (Iterator<Powerup> it = powerups.iterator(); it.hasNext(); ) {
			Powerup p = it.next();
			if (p.getBounds().overlaps(paddle)) {
				p.applyPowerup();
				decreaseNumPowerups(determineIndex(p));
				it.remove();
			}
		}
	}
	
	public void checkBelowScreen() {
		for (Iterator<Powerup> it = powerups.iterator(); it.hasNext(); ) {
			Powerup p = it.next();
			if ((p.getBounds().y + p.getBounds().height) < 0) {
				decreaseNumPowerups(determineIndex(p));
				it.remove();
			}
		}
	}
	
	private void resetNumPowerups() {
		for (int i = 0; i < 5; i++) {
			numPowerups.set(i, 0);
		}
	}
	
	public void dispose() {
		powerups.clear();
		resetNumPowerups();
	}
}
