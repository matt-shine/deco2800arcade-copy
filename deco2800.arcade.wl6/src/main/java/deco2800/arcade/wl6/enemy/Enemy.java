package deco2800.arcade.wl6.enemy;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wl6.DoodadInfo;
import deco2800.arcade.wl6.GameModel;
import deco2800.arcade.wl6.Mob;
import deco2800.arcade.wl6.Player;
import deco2800.arcade.wl6.Projectile;
import deco2800.arcade.wl6.WL6Meta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Enemy extends Mob {

    /**
     * Six possible states
     * An enemy starts in either Stand or Path states.  Enters chase state when player is spotted or gunfire is heard.
     * Once enemy has left the stand or path states, it cannot reenter them.
     */
    public enum STATES {
        NO_STATE,   // No state, error
        STAND,      // Idle state 1, motionless
        PATH,       // Idle state 2, following waypoints
        CHASE,      // Player spotted, chasing/dodging
        PAIN,       // Reacting to pain, interrupts any previous action/state
        ATTACK,     // Attacking the player
        DIE         // Death
    }

    // current state
    private STATES state = STATES.NO_STATE;
    // State tick
    private float stateTime;
    private STATES nextState;
    //
    private boolean pathing;
    //
    private float pathSpeed;
    //
    private float chaseSpeed;
    //
    private int pathGoal = 1;
    private int pathLast = 0;
    private int pathLoopStart = 0;
    
    private float stateChangeTime = 1;
    
    public float getStateChangeTime() {
		return stateChangeTime;
	}

	public void setStateChangeTime(float stateChangeTime) {
		this.stateChangeTime = stateChangeTime;
	}

	// path list
    private List<Vector2> path;
    // suffers from pain (they have an animation that they do nothing in when they get hit, interrupts their current action)
    private boolean pain;
    // damage
    private int damage;


    public Enemy(int uid) {
        super(uid);
        stateTime = 0;
        nextState = null;
    }

    @Override
    public void init(GameModel model) {
        super.init(model);
        if (pathing) {
            calculatePath(model);
        }
        
    }

    @Override
    public void tick(GameModel gameModel) {
        super.tick(gameModel);

        if (state == STATES.DIE) {
        	return;
        }
        
        stateTime += gameModel.delta();
        if (stateTime > stateChangeTime && nextState != null) {
            this.instantStateChange(nextState);
            nextState = null;
        }
        
        
        if (canSee(gameModel.getPlayer(), gameModel)){
        	delayedStateChange(STATES.ATTACK);
        }
        
        
        if (state == STATES.PATH && path != null && path.size() > 1) {
            path();
        }
        

        
        if (state == STATES.ATTACK) {
        	shootAtPlayer(gameModel);
            instantStateChange(STATES.CHASE);
            delayedStateChange(STATES.ATTACK);
        }

        if (this.getHealth() <= 0) {
        	instantStateChange(STATES.DIE);
        	this.setTextureName("headstone");
        	this.setVel(new Vector2(0, 0));
        }
    }

    
    public void initialiseFromEnemyData(DoodadInfo d) {
    	
    	setTextureName(d.texture);
        setPathing(d.pathing);
        if (d.pathing) {
        	instantStateChange(STATES.PATH);
        } else {
        	instantStateChange(STATES.STAND);
        }
        this.setAngle(WL6Meta.dirToAngle(d.direction));
        
    }
    

    public void setPathing(boolean pathing) {
        this.pathing = pathing;
    }

    /**
     * Tells the enemy to change states
     * @param state state to change the enemy to
     */
    public void delayedStateChange(STATES state) {
    	if (nextState != state) {
	        nextState = state;
	        stateTime = 0;
    	}
    }
    
    
    public void instantStateChange(STATES state) {
        this.state = state;
        nextState = null;
        stateTime = 0;
    }

    
    public void shootAtPlayer(GameModel g) {
    	Player p = g.getPlayer();
    	Projectile bullet = new Projectile(0, 10, true, "worm");
    	g.addDoodad(bullet);
    	bullet.setPos(this.getPos());
    	bullet.setVel(p.getPos().sub(bullet.getPos()).nor().mul(0.2f));
    }
    
    
    // follow patrol path
    public void path() {
    	
    	//if we've passed the next pathing node...
    	Vector2 goal = path.get(pathGoal).cpy().add(0.5f, 0.5f);
    	Vector2 last = path.get(pathLast).cpy().add(0.5f, 0.5f);
    	if (last.dst(goal) <= last.dst(getPos())) {
    		
    		setPos(goal);
    		pathLast = pathGoal;
    		pathGoal++;
    		if (pathGoal >= path.size()) {
    			pathGoal = this.pathLoopStart;
    		}
    		
    	}
    	this.setVel(new Vector2().add(goal).sub(last).nor().mul(0.1f));

    	
    }



    @Override
    public void takeDamage(GameModel model, int damage) {
        int d = damage;
        if (state == STATES.STAND || state == STATES.PATH) {
            d = d * 2;
        }
        if (feelsPain()) {
        	instantStateChange(STATES.PAIN);
        	delayedStateChange(STATES.CHASE);
        	setHealth(getHealth() - d);
        }
        else {
            setHealth(getHealth() - d);
        }
    }
    

    @Override
    public void doDamage(GameModel gameModel) {
        float dist = this.getPos().dst(gameModel.getPlayer().getPos());
        boolean speed = true;
        boolean look = true;
        int damage = calcDamage((int)dist, speed, look);
        gameModel.getPlayer().takeDamage(gameModel, damage);
    }

    /**
     * Damage Calculation
     *
     * @param dist Distance between enemy and player (in number of squares)
     * @param speed Is the player running or not
     * @param look Can the player see the shooter or not
     * @return Damage to be dealt.
     */
    public int calcDamage(int dist, boolean speed, boolean look) {
        boolean hit = false;
        if (randInt(0, 255, getRand()) < ((speed ? 160 : 256) - (dist * (look ? 16 : 8)))) {
            hit = true;
        }

        setDamage(randInt(0, 255, getRand()));

        if (hit) {
            if (dist < 2) {
                setDamage(getDamage() / 4);
            }
            else if (dist >= 2 && dist < 4) {
                setDamage(getDamage() / 8);
            }
            else if (dist >= 4) {
                setDamage(getDamage() / 16);
            }
            else {
                setDamage(0);
            }
        }
        else {
            setDamage(0);
        }

        return getDamage();
    }

    public void calculatePath(GameModel gameModel) {
        path = new ArrayList<Vector2>();
        HashSet<Vector2> usedWaypoints = new HashSet<Vector2>();

        int x = (int)getPos().x;
        int y = (int)getPos().y;
        int angle = (int)this.getAngle();
        path.add(new Vector2(x, y));

        boolean complete = false;

        while (!complete) {
            switch (angle) {
                case 0:
                    x = x + 0;
                    y = y - 1;
                    break;
                case 45:
                    x = x - 1;
                    y = y - 1;
                    break;
                case 90:
                    x = x - 1;
                    y = y + 0;
                    break;
                case 135:
                	x = x - 1;
                    y = y + 1;
                    break;
                case 180:
                    x = x + 0;
                    y = y + 1;
                    break;
                case 225:
                	x = x + 1;
                    y = y + 1;
                    break;
                case 270:
                    x = x + 1;
                    y = y + 0;
                    break;
                case 315:
                	x = x + 1;
                    y = y - 1;
                    break;
            }
            if (path.size() > 10000) {
            	System.out.println("infinite loop in a path");
                return;
            }
            if (WL6Meta.block(gameModel.getMap().getTerrainAt(x, y)).texture != null) {
                System.out.println("waypoints led into a wall");
                return;
            }
            path.add(new Vector2(x, y));
            if (gameModel.getWaypoint(x, y) != null) {
                if(usedWaypoints.contains(new Vector2(x, y))) {
                	pathLoopStart = path.indexOf(new Vector2(x, y));
                    complete = true;
                } else {
                    angle = (int)WL6Meta.dirToAngle(gameModel.getWaypoint(x, y));
                }
            }
        }
    }

	public float getPathSpeed() {
		return pathSpeed;
	}

	public void setPathSpeed(float pathSpeed) {
		this.pathSpeed = pathSpeed;
	}

	public boolean feelsPain() {
		return pain;
	}

	public void setPain(boolean pain) {
		this.pain = pain;
	}

	public float getChaseSpeed() {
		return chaseSpeed;
	}

	public void setChaseSpeed(float chaseSpeed) {
		this.chaseSpeed = chaseSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public boolean isDead() {
		return this.state == STATES.DIE;
	}
	
	
}

