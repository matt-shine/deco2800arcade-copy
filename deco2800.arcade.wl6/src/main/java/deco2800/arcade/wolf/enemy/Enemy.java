package deco2800.arcade.wolf.enemy;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wolf.Doodad;
import deco2800.arcade.wolf.DoodadInfo;
import deco2800.arcade.wolf.GameModel;
import deco2800.arcade.wolf.Mob;
import deco2800.arcade.wolf.Pickup;
import deco2800.arcade.wolf.Player;
import deco2800.arcade.wolf.Projectile;
import deco2800.arcade.wolf.WL6Meta;
import deco2800.arcade.wolf.WL6Meta.KEY_TYPE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Enemy extends Mob {

    /**
     * Six possible states
     * An enemy starts in either Stand or Path states.  Enters chase state when player is spotted or gunfire is heard.
     * Once enemy has left the stand or path states, it cannot reenter them.
     */
    public enum STATES {
        NO_STATE,   // No state, error
        IDLE,       // Idle state 0, motionless
        STAND,      // Idle state 1, motionless
        PATH,       // Idle state 2, following waypoints
        CHASE,      // Player spotted, chasing/dodging
        PAIN,       // Reacting to pain, interrupts any previous action/state
        ATTACK,     // Attacking the player
        DIE         // Death
    }

    // current state
    private STATES state = STATES.NO_STATE;
    // time till we set state to nextstate
    private float stateTime;
    //the next state
    private STATES nextState;
    //move speed
    private float speed;
    //whether to use pathing
    private boolean pathing = false;
    //index of next pathing way point
    private int pathGoal = 1;
    //index of last pathing way point
    private int pathLast = 0;
    //when we reach the end of the path, instead of going back to 0, go back to here
    private int pathLoopStart = 0;
    //idle time between states
    private float stateChangeTime = 0.5f;
    // path list
    private List<Vector2> path;
    // suffers from pain (they have an animation that they do nothing in when they get hit, interrupts their current action)
    private boolean pain;
    //the intermediate goal for the player chasing logic
    private Vector2 chaseGoal = null;
    //the intermediate goal for the player chasing logic
    private Vector2 chaseLast = null;
    //the odds that the enemy will shoot at the end of a chase step
    private float shootChance = 0.6f;
    //the chance that the enemy will shoot again after just shooting
    private float repeatShootChance = 0.3f;
	//the distance we try to keep away from the player
    private float personalSpace = 4f;
    
	private int ammoDrop = 0;
    @SuppressWarnings("unused")
    private int gunDrop = 0;
    
    private KEY_TYPE keyDrop = null;

    
    

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
        speed = 0.04f;//TODO remove this and let each enemy decide it's own speed
        setHealth(getStartingHealth(model.getDifficulty()));
    }

    @Override
    public void tick(GameModel gameModel) {
        super.tick(gameModel);

        //stay dead forever
        if (state == STATES.DIE) {
            return;
        }

        //change to the next state soon
        stateTime += gameModel.delta();
        if (stateTime > stateChangeTime && nextState != null) {
            this.instantStateChange(nextState);
            nextState = null;
        }


        //if idle do nothing
        if (state == STATES.IDLE) {
            this.setVel(new Vector2(0, 0));
            if (nextState == null) delayedStateChange(STATES.CHASE);
            return;
        }


        //if we can see the player and we haven't seen him yet, chase him
        if ((this.state == STATES.PATH || this.state == STATES.STAND) && canSee(gameModel.getPlayer(), gameModel)){
        	this.setVel(new Vector2(0, 0));
        	instantStateChange(STATES.IDLE);
        	delayedStateChange(STATES.CHASE);
        }


        //move along path
        if (state == STATES.PATH && path != null && path.size() > 1) {
            path();
        }



        //attack player
        if (state == STATES.ATTACK) {
            shootAtPlayer(gameModel);
            this.setVel(new Vector2(0, 0));
            instantStateChange(STATES.IDLE);
            if (Math.random() < repeatShootChance) {
            	delayedStateChange(STATES.ATTACK);
            } else {
            	delayedStateChange(STATES.CHASE);
            }
            
        }


        //chase the player
        if (state == STATES.CHASE) {
            followPlayer(gameModel);
        }


        //check if dead
        if (this.getHealth() <= 0) {
            instantStateChange(STATES.DIE);
            this.setTextureName("headstone");
            this.setVel(new Vector2(0, 0));
            this.dropItems(gameModel);
        }
    }
    
    
    
    /**
     * Intended to be overridden
     * @param difficulty
     * @return
     */
    public int getStartingHealth(int difficulty) {
        return 0;
    }
    
    
    
    
    /**
     * Initialise the enemy
     * @param d
     */
    public void initialiseFromEnemyData(DoodadInfo d) {

        setTextureName(d.texture);
        setPathing(d.pathing);
        ammoDrop = d.ammo;
        EnemyType e = d.enemytype;
        if (e == EnemyType.FETTGESICHT ||
                e == EnemyType.GIFTMACHER ||
                e == EnemyType.GRETEL ||
                e == EnemyType.HANS ||
                e == EnemyType.HITLER ||
                e == EnemyType.SCHABBS) {
            this.keyDrop = KEY_TYPE.GOLD;
        }
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
    	int damage = calcDamage((int) p.getPos().dst(getPos()), this.getVel().len() == 0, false);
    	Projectile bullet = new Projectile(0, damage, true, "worm");
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
        this.setVel(new Vector2().add(goal).sub(last).nor().mul(speed));


    }
    

    
    
    
    
    /**
     * Called when the state is CHASE
     * @param g
     */
    protected void followPlayer(GameModel g) {
    	Player p = g.getPlayer();
    	
    	float dist = p.getPos().dst(this.getPos());
    	boolean los = this.canSee(g.getPlayer(), g);
    	
    	
    	//if we've passed the next chase goal node...
    	if (chaseGoal == null || chaseLast == null) {
    		chaseLast = new Vector2(this.getPos());
    		chaseGoal = bestChaseGoal(g);
    	}
    	
    	
    	if (chaseLast.dst(chaseGoal) <= chaseLast.dst(getPos())) {
    		
    		setPos(chaseGoal);
    		chaseLast = chaseGoal;
			chaseGoal = null;
			
    		if (los && dist < 15 && Math.random() < this.shootChance) {
    			instantStateChange(STATES.IDLE);
    			delayedStateChange(STATES.ATTACK);
    			this.setVel(new Vector2(0, 0));
    		} else {
    			chaseGoal = bestChaseGoal(g);
    		}
    		
    	}
    	
    	
    	if (chaseGoal != null && chaseLast != null) {
        	this.setVel(new Vector2().add(chaseGoal).sub(this.getPos()).nor().mul(speed));
    	} else {
    		this.setVel(new Vector2(0, 0));
    	}

    }
    
    
    
    
    
    /**
     * returns the closest tile to the player that doesn't have anything in it,
     * but avoids tiles closer than (4.0 + random() * 1) units to the player unless there is no other choice
     * @param g
     * @return
     */
    protected Vector2 bestChaseGoal(GameModel g) {
    	
    	TreeMap<Float, Vector2> goals = new TreeMap<Float, Vector2>();
    	
    	Vector2[] dirs = new Vector2[]{
    			new Vector2(0, 1),
    			new Vector2(1, 0),
    			new Vector2(-1, 0),
    			new Vector2(0, -1),
    	};
    	
    	Player p = g.getPlayer();
    	
    	Vector2 me = this.getBlockPos().add(0.5f, 0.5f);
    	
    	for (int i = 0; i < dirs.length; i++) {
    		
    		Vector2 hypothetical = me.cpy().add(dirs[i]);
    		if (this.isWalkableTile(g, (int) Math.floor(hypothetical.x), (int) Math.floor(hypothetical.y))) {

    			goals.put((float) (hypothetical.dst(p.getPos()) + Math.random()), hypothetical.cpy());
    			
    		}
    		
    	}
		
    	SortedMap<Float, Vector2> greater = goals.tailMap(personalSpace, true);
    	SortedMap<Float, Vector2> less = goals.headMap(personalSpace, false);
    	if (greater.size() != 0) {
    		return greater.get(greater.firstKey());
    	}
		if (less.size() != 0) {
			return less.get(less.lastKey());
		}
		
    	return me;

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
        	this.setVel(new Vector2(0, 0));

        } else {
            setHealth(getHealth() - d);
        }
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

        int damage = randInt(0, 255, getRand());

        if (hit) {
            if (dist < 2) {
                damage = damage / 4;
            }
            else if (dist >= 2 && dist < 4) {
                damage = damage / 8;
            }
            else if (dist >= 4) {
                damage = damage / 16;
            }
            else {
                damage = 0;
            }
        }
        else {
            damage = 0;
        }

        return damage;
    }
    
    
    /**
     * 
     * @param g
     */
    protected void dropItems(GameModel g) {

        Doodad d = null;

        if (this.keyDrop != null) {
            d = new Pickup(0, this.keyDrop);
            d.setTextureName("goldkey");
        } else {
            d = new Pickup(0, 0, 0, ammoDrop, 0);
            d.setTextureName("ammo");
        }


        do {

            d.setPos(this.getPos().add((float) (0.7 * Math.random() - 0.35), (float) (0.7 * Math.random() - 0.35)));

        } while (WL6Meta.hasSolidBlockAt((int) d.getBlockPos().x, (int) d.getBlockPos().y, g.getMap()));

        g.addDoodad(d);
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
            if (path.size() > 1000) {
            	//System.out.println("infinite loop in a path");
                return;
            }
            if (WL6Meta.block(gameModel.getMap().getTerrainAt(x, y)).texture != null) {
                //System.out.println("waypoints led into a wall");
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean feelsPain() {
        return pain;
    }

    public void setPain(boolean pain) {
        this.pain = pain;
    }

    public boolean isDead() {
        return this.state == STATES.DIE;
    }

    public float getStateChangeTime() {
        return stateChangeTime;
    }

    public void setStateChangeTime(float stateChangeTime) {
        this.stateChangeTime = stateChangeTime;
    }

    public float getShootChance() {
        return shootChance;
    }

	public void setShootChance(float shootChance) {
		this.shootChance = shootChance;
	}
	
    public float getPersonalSpace() {
		return personalSpace;
	}

	public void setPersonalSpace(float personalSpace) {
		this.personalSpace = personalSpace;
	}
	
    public float getRepeatShootChance() {
		return repeatShootChance;
	}

	public void setRepeatShootChance(float repeatShootChance) {
		this.repeatShootChance = repeatShootChance;
	}


}

