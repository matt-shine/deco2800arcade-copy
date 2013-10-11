package deco2800.arcade.wl6;

import java.util.HashSet;

public class Player extends Mob {

    public static final float SPEED = 3f;

    
    private int health = 0;
    private int points = 0;
    private int currentGun = 0;
    private HashSet<Integer> guns = new HashSet<Integer>();
    private int ammo = 0;

    


	public Player(int uid) {
        super(uid);
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

    
    
    
    
    
    
    public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}
	
	
	public void addHealth(int health, boolean overheal) {
		this.health = Math.min(this.health + health, overheal ? 150 : 100);
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
		this.ammo = ammo;
	}

	public void addAmmo(int ammo) {
		this.ammo = Math.min(this.ammo + ammo, 99);
	}
	
	
	public void addGun(int gun) {
		
		if (!guns.contains(gun)) {
			this.currentGun = gun;
		}
		
		this.guns.add(gun);
		
	}
	
}






