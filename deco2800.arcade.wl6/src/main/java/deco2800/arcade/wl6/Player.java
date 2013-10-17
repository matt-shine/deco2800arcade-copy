package deco2800.arcade.wl6;

import java.util.HashSet;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;

public class Player extends Mob {

    public static final float SPEED = 3f;

    
    private int STARTING_HEALTH = 100;
    private int points = 0;
    private int currentGun = 1;
    private HashSet<Integer> guns = new HashSet<Integer>();
    private int ammo = 16;
    private HashSet<KEY_TYPE> keys = new HashSet<KEY_TYPE>();
	
    


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
	
	
	public void addKey(KEY_TYPE k) {
		keys.add(k);
	}
	
	public boolean hasKey(KEY_TYPE k) {
		return keys.contains(k);
	}


    @Override
    public void doDamage() {
        float dist = this.getPos().dst(gameModel.getPlayer().getPos());
        int damage = calcDamage((int)dist);
        gameModel.getPlayer().takeDamage(damage);
    }

    /**
     * Damage Calculation
     *
     * @param dist Distance between enemy and player (in number of squares)
     * @return Damage to be dealt.
     */
    public int calcDamage(int dist) {
        boolean hit = false;
        if (dist > 4) {
            if (randInt(0, 255, rand) / 12 < dist) {
                hit = true;
            }
        }
        else {
            hit = true;
        }

        int damage = randInt(0, 255, rand);

        if (hit) {
            if (dist < 2) {
                damage = damage / 4;
            }
            else {
                damage = damage / 6;
            }
        }
        else {
            damage = 0;
        }

        return damage;
    }
	
}






