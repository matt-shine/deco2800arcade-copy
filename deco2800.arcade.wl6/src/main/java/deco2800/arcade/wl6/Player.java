package deco2800.arcade.wl6;

import java.util.HashSet;

public class Player extends Mob {

    public static final float SPEED = 3f;

    
    private int STARTING_HEALTH = 100;
    private int points = 0;
    private int currentGun = 1;
    private HashSet<Integer> guns = new HashSet<Integer>();
    private int ammo = 10;

    


	public Player(int uid) {
        super(uid);
        setHealth(STARTING_HEALTH);
        guns.add(0);
        guns.add(1);
    }


    public void draw(Renderer renderer) {
        if (renderer.isDebugMode()) {
            renderer.drawBasicSprite(getTextureName(), getPos().x, getPos().y, -getAngle());
        }
        //no super call
    }

    @Override
    public void tick(GameModel model) {
        super.tick(model);
    }


    public void addHealth(int health, boolean overheal) {
        setHealth(Math.min(this.health + health, overheal ? 150 : 100));
    }


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public void addPoints(int points) {
		this.points += points;
	}


	public int getCurrentGun() {
		return currentGun;
	}


	public void setCurrentGun(int currentGun) {
		if (this.guns.contains(currentGun)) {
			this.currentGun = currentGun;
		}
	}


	public int getAmmo() {
		return ammo;
	}


	public void setAmmo(int ammo) {
		if (this.ammo == 0 && ammo > 0) {
			if (guns.contains(3)) {
				this.currentGun = 3;
			} else if (guns.contains(2)) {
				this.currentGun = 2;
			} else {
				this.currentGun = 1;
			}
		}
		this.ammo = Math.min(ammo, 99);
		if (ammo <= 0) {
			this.ammo = 0;
			this.setCurrentGun(0);
		}
	}

	public void addAmmo(int ammo) {
		setAmmo(this.ammo + ammo);
	}
	
	
	public void addGun(int gun) {
		
		if (!guns.contains(gun)) {
			this.currentGun = gun;
		}
		
		this.guns.add(gun);
		
	}
	
}






