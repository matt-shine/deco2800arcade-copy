package deco2800.arcade.wl6;

import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;

public class Player extends Mob {

    public static final float SPEED = 3f;

    
    private int STARTING_HEALTH = 100;
    private float BB_SIZE = 0.4f;
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
    	
    	Vector2 adjustedVelocity = new Vector2(getVel()).mul(model.delta() * 60);
    	
    	if (move(model, adjustedVelocity)) {
    		//we just moved as intended
    	} else if (move(model, new Vector2(adjustedVelocity.x, 0))) {
    		//we just moved, but only in the x direction
    	} else if (move(model, new Vector2(0, adjustedVelocity.y))) {
    		//we just moved, but only in the y direction
    	} else {
    		//we are stuck
    	}
    	
    	
    	
    }
    
    
    /**
     * tries to move the object. returns true if successful
     * @param model
     * @param vec
     * @return
     */
    private boolean move(GameModel model, Vector2 vec) {
    	
    	Vector2 targetPos = this.getPos().add(vec);
    	int x1 = (int) Math.floor(targetPos.x - BB_SIZE / 2);
    	int y1 = (int) Math.floor(targetPos.y - BB_SIZE / 2);
    	int x2 = (int) Math.floor(targetPos.x + BB_SIZE / 2);
    	int y2 = (int) Math.floor(targetPos.y + BB_SIZE / 2);
    	
    	for (int x = x1; x <= x2; x++) {
    		for (int y = y1; y <= y2; y++) {
    		    if (model.getCollisionGrid().getSolidAt(x, y) != 0) {
    		    	return false;
    		    }
	    	}
    	}
    	
    	setPos(getPos().add(vec));
    	return true;
    	
    }

    
    public void addHealth(int health, boolean overheal) {
        setHealth(Math.min(this.getHealth() + health, overheal ? 150 : 100));
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
    public void doDamage(GameModel gameModel) {
        // FIXME this is currently doing damage to yourself :P
        float dist = this.getPos().dst(gameModel.getPlayer().getPos());
        int damage = calcDamage((int)dist);
        gameModel.getPlayer().takeDamage(gameModel, damage);
    }

    @Override
    public void takeDamage(GameModel model, int damage) {
        if (model.getDifficulty() == 1) {
            setHealth(getHealth() - (damage / 4));
        }
        else {
            setHealth(getHealth() - damage);
        }
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
            if (randInt(0, 255, getRand()) / 12 < dist) {
                hit = true;
            }
        }
        else {
            hit = true;
        }

        int damage = randInt(0, 255, getRand());

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






