package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;
import deco2800.arcade.hunter.screens.GameScreen;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Entity {
    /**
     * Constant of JUMP VELOCITY
     */
    private static final int JUMP_VELOCITY = 12;
    /**
     * The current animation for the player
     */
    private Animation currAnim;
    /**
     * Lives counter for the player
     */
    private int lives;
    /**
     * A hashmap list of all the players
     */
    private HashMap<String, Animation> animationList = new HashMap<String, Animation>();
    private String Weapon; //Weapon that the player is wielding
    private boolean animLoop; //Boolean if the animation loops
    private long attackTime = 0; //Time when the player last attacked
    private long damageTime = 0; //Time when last hit by a monster.
    private long cooldownModifier = 0; //Modifies the cooldown of the attacks
    private long buffTime = 0; //Time when item was picked up
    private GameScreen gamescreen; //Gamescreen which the player is in
    private int animalsKilled; //Number of animals killed by the player
    private int weaponAmmo; //Ammunition for the weapon the player is wielding
    private long deathTime = 0; //The time when the player is declared as dead
    private boolean dead; //Boolean of whether the player is dead or not
    private int score = 0; //The player's score
    /**
     * The class type of the entity
     */
    private String classType = "Player";
    private int multiplier; //The score multiplier
    private boolean invulnerable; //Boolean of whether the player is invulnerable
    private boolean blink = false;//Boolean of whether they were
    private State state = State.RUNNING;
    //Sound files to be loaded
    private Sound pickup = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));
    private Sound death = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
    private Sound hurt = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));

    //States used to determine how to draw the player
    private enum State {
        RUNNING, JUMPING, ATTACK, FALLING, DEAD, DAMAGED
    }

    public Player(Vector2 pos, float width, float height, GameScreen game) {
        super(pos, width, height);
        loadAnims();
        lives = 3;
        Weapon = "Bow";
        currAnim = runAnimation();
        multiplier = 1;
        weaponAmmo = 10;
        this.gamescreen = game;
        dead = false;
    }

    /**
     * Loads all the player animations and stores them in a HashMap
     */
    private void loadAnims() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Gdx.files.internal("player.xml").read());

            NodeList nList = doc.getElementsByTagName("animation");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    animationList.put(eElement.getAttribute("id") + "running", createAnimation(3, new Texture(eElement.getElementsByTagName("running").item(0).getTextContent()), 0.1f));
                    animationList.put(eElement.getAttribute("id") + "attack", createAnimation(2, new Texture(eElement.getElementsByTagName("attack").item(0).getTextContent()), 0.3f));
                    animationList.put(eElement.getAttribute("id") + "jump", createAnimation(1, new Texture(eElement.getElementsByTagName("jumping").item(0).getTextContent()), 2f));
                    animationList.put(eElement.getAttribute("id") + "fall", createAnimation(1, new Texture(eElement.getElementsByTagName("falling").item(0).getTextContent()), 2f));
                    animationList.put(eElement.getAttribute("id") + "damage", createAnimation(1, new Texture(eElement.getElementsByTagName("damage").item(0).getTextContent()), 1f));
                    animationList.put(eElement.getAttribute("id") + "death", createAnimation(1, new Texture(eElement.getElementsByTagName("death").item(0).getTextContent()), 10f));
                }
            }
            System.out.println("Player Animations Loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an animation
     *
     * @param frames Determines how many frames there are in the texture
     * @param text   The texture to be created into an animation
     * @return Animation of the Texture
     */
    private Animation createAnimation(int frames, Texture text, float speed) {
        TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth()
                / frames, text.getHeight());
        TextureRegion[] animFrames = new TextureRegion[frames];
        for (int j = 0; j < frames; j++) {
            animFrames[j] = tmp[0][j];
        }
        return new Animation(speed, animFrames);
    }

    /**
     * Makes the player jump
     */
    public void jump() {
        setJumpVelocity(JUMP_VELOCITY);
        this.collider.bottom = false;
    }

	/*
     *
	 * Player Methods
	 * 
	 */

    /**
     * Attack trigger for the player
     */
    public void attack() {
        if (attackTime + Config.PLAYER_ATTACK_COOLDOWN - cooldownModifier < System.currentTimeMillis()) {
            if (this.state != State.ATTACK && this.state != State.DAMAGED) {
                Sound attack = Gdx.audio.newSound(Gdx.files.internal("attack.wav"));
                if (Hunter.State.getPreferencesManager().isSoundEnabled()) {
                    attack.play(Hunter.State.getPreferencesManager().getVolume());
                }
                state = State.ATTACK;
                currAnim = attackAnimation();
                attackTime = System.currentTimeMillis();
                weaponAmmo -= 1;
                if (weaponAmmo <= 0) {
                    Weapon = "KnifeandFork";
                }
                if (Weapon.equals("KnifeandFork")) {
                    attackTime -= 200;
                    cooldownModifier = 0;
                } else if (Weapon.equals("Trident")) {
                    cooldownModifier = 100;
                } else if (Weapon.equals("Spear")) {
                    attackTime += 100;
                    cooldownModifier = -200;
                } else if (Weapon.equals("Bow")) {
                    cooldownModifier = 0;
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        // Everything depends on everything else here, may have to rearrange, or
        // even double up on checks
        setJumpVelocity(getJumpVelocity() - delta * Hunter.State.gravity);
        setX(getX() + delta * Hunter.State.playerVelocity.x);

        if (state != State.DEAD) {
            if (collider.bottom && Hunter.State.playerVelocity.y < 0) {
                setJumpVelocity(0);
                setY(gamescreen.getForeground().getColumnTop(getX()));
            } else {
                setY(getY() + getJumpVelocity());
            }

            if (Hunter.State.playerVelocity.x < Hunter.State.gameSpeed) {
                Hunter.State.playerVelocity.x += 100 * delta;
            }

            // Update the player state
            if (isGrounded() && this.state != State.ATTACK && this.state != State.DAMAGED && this.state != State.DEAD) {
                Hunter.State.playerVelocity.y = 0;
                this.state = State.RUNNING;
                currAnim = runAnimation();
            } else if (Hunter.State.playerVelocity.y > 0 && this.state != State.ATTACK && this.state != State.DEAD) {
                this.state = State.JUMPING;
                currAnim = jumpAnimation();
            } else if (Hunter.State.playerVelocity.y < -1 && this.state != State.ATTACK && this.state != State.DEAD) {
                this.state = State.FALLING;
                currAnim = fallAnimation();
            }

            score += 100 * delta * multiplier;
        }
        checkDist();
        checkTimers();
    }

    /**
     * Checks the Timers
     */
    private void checkTimers() {
        if (attackTime + Config.PLAYER_ATTACK_TIMEOUT > System.currentTimeMillis() && !dead) {
            this.state = State.ATTACK;
            currAnim = attackAnimation();
        } else {
            this.state = State.RUNNING;
        }

        if (damageTime + Config.PLAYER_BLINK_TIMEOUT > System.currentTimeMillis() && !dead) {
            this.state = State.DAMAGED;
            currAnim = damageAnimation();
        } else {
            this.blink = false;
        }

        if (deathTime + Config.PLAYER_BLINK_TIMEOUT > System.currentTimeMillis()) {
            this.state = State.DEAD;
            currAnim = deathAnimation();
        } else {
            if (dead) {
                gamescreen.gameOver();
            }
        }

        if (buffTime + 3000 < System.currentTimeMillis()) {
            if (invulnerable = true)
                invulnerable = false;
            if (multiplier != 1) {
                multiplier = 1;
                gamescreen.setMultiplier(1);
            }
        }
    }

	/*
	 * 
	 * Checkers
	 * 
	 */

    /**
     * Checks the distances for achievement purposes
     */
    private void checkDist() {
        if (this.getCurrentDistance() >= 100) {
            gamescreen.getGameReference().incrementAchievement("hunter.100m");
        }
        if (this.getCurrentDistance() >= 200) {
            gamescreen.getGameReference().incrementAchievement("hunter.200m");
        }
        if (this.getCurrentDistance() >= 400) {
            gamescreen.getGameReference().incrementAchievement("hunter.400m");
        }
        if (this.getCurrentDistance() >= 800) {
            gamescreen.getGameReference().incrementAchievement("hunter.800m");
        }
        if (this.getCurrentDistance() >= 2000) {
            gamescreen.getGameReference().incrementAchievement("hunter.Marathoner");
        }
    }

    /**
     * Checks if the player has any lives left
     */
    private void checkLives() {
        if (lives <= 0) {
            if (Hunter.State.getPreferencesManager().isSoundEnabled()) {
                death.play(Hunter.State.getPreferencesManager().getVolume());
            }
            this.state = State.DEAD;
            multiplier = 0;
			Hunter.State.playerVelocity = new Vector2(0,0);
			deathTime = System.currentTimeMillis();
			this.currAnim = deathAnimation();
			dead = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
        animLoop = state == State.RUNNING;
        TextureRegion currFrame = currAnim.getKeyFrame(stateTime, animLoop);

        if (blink) {
            batch.setColor(1f, 1f, 1f, 0.5f);
        } else {
            batch.setColor(1f, 1f, 1f, 1f);
        }
        if (dead) {
            batch.draw(currFrame, getX(), getY(), getWidth() * 2, getHeight() / 2);
        } else {
            batch.draw(currFrame, getX(), getY(), getWidth(), getHeight());
        }
        batch.setColor(1f, 1f, 1f, 1f);
        if (invulnerable) {
			Texture inv = new Texture("textures/invulnerability.png");
			batch.draw(inv,getX()-10,getY()-10,getWidth()+20,getHeight()+20);
		}
	}

    /**
     * Checks for entity collisions
     */
    @Override
    public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
        ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
        Player player = this;
        for (Entity e : entities) {
            if (player.getBounds().overlaps(e.getBounds())) {
                if (e.getType().equals("Animal")) {
                    if (player.state == State.ATTACK)
                        collisions.add(new EntityCollision(player, e,
                                CollisionType.PLAYER_PROJECTILE_C_ANIMAL));

                    else
                        collisions.add(new EntityCollision(player, e,
                                CollisionType.WORLD_PROJECTILE_C_PLAYER));

                }
                if (e.getType().equals("Items")) {
                    collisions.add(new EntityCollision(player, e,
                            CollisionType.ITEM_C_PLAYER));
                }
                if (e.getType().equals("MapEntity")) {
                    collisions.add(new EntityCollision(player, e, CollisionType.MAP_ENTITY_C_PLAYER));
                }
            }

        }
        return collisions;
    }

    /**
     * Handles entity collisions
     */
    @Override
    public void handleCollision(Entity e, EntityCollection entities) {
        if (e == null) {
            lives = 0;
        } else if (e.getType().equals("Items")) {
            entities.remove(e);
            if (Hunter.State.getPreferencesManager().isSoundEnabled()) {
                pickup.play(Hunter.State.getPreferencesManager().getVolume());
            }
            if (((Items) e).getItemType() == Items.Type.WEAPON) {
                setWeapon(((Items) e).getItem());
                weaponAmmo = 10;
                gamescreen.getGameReference().incrementAchievement("hunter.arsenal." + ((Items) e).getItem());
            } else {
                applyPlayerBuff(((Items) e).getItem());
            }
        } else if (e.getType().equals("Animal")) {
            if (getState() == State.ATTACK) {
                if (!((Animal) e).isDead()) {
                    addAnimalKilled();
                    score = score + 200 * multiplier;
                }
                ((Animal) e).dead();
            } else {
                if (!invulnerable && !blink && !((Animal) e).isDead()) {
                    if (Hunter.State.getPreferencesManager().isSoundEnabled()) {
                        hurt.play(Hunter.State.getPreferencesManager().getVolume());
                    }
                    this.blink = true;
                    damageTime = System.currentTimeMillis();
					loseLife();
					checkLives();
				}
			}
		} else if(e.getType().equals("MapEntity") && !((MapEntity) e).getEntityType().equals("arrow")){
			if (!invulnerable && !blink && !dead){
				String type = ((MapEntity)e).getEntityType();
				applyPlayerDebuff((MapEntity)e);
			}
		}
	}

	private void applyPlayerDebuff(MapEntity ent){
		if (ent.getEntityType().equals("net")){
			Hunter.State.playerVelocity.x = 0;
			gamescreen.getEntites().remove(ent);
		}else if(ent.getEntityType().equals("bomb")){
			ent.explode();
		}else if(ent.getEntityType().equals("spike trap")){
			if (Hunter.State.getPreferencesManager().isSoundEnabled()){
				hurt.play(Hunter.State.getPreferencesManager().getVolume());
			}
			this.blink = true;
			damageTime = System.currentTimeMillis();
			loseLife();
			checkLives();
		}else if(ent.getEntityType().equals("deathShroom")){
			if (Hunter.State.getPreferencesManager().isSoundEnabled()){
				hurt.play(Hunter.State.getPreferencesManager().getVolume());
			}
			score -= 1000;
			gamescreen.getEntites().remove(ent);
		}else if(ent.getEntityType().equals("explosion")){
			if (Hunter.State.getPreferencesManager().isSoundEnabled()){
				hurt.play(Hunter.State.getPreferencesManager().getVolume());
			}
			this.blink = true;
			damageTime = System.currentTimeMillis();
			loseLife();
			checkLives();
		}
	}
	
	/**
	 * Applies the buffs that the player receives
	 * @param item - String of item to be applied
	 */
	private void applyPlayerBuff(String item) {
		if (item.equals("DoublePoints")){
			multiplier = multiplier * 2;
			gamescreen.setMultiplier(multiplier);
			buffTime = System.currentTimeMillis();
			gamescreen.getGameReference().incrementAchievement("hunter.superpowered.DoublePoints");
		}
			
		if (item.equals("ExtraLife")){
			addLife();
			gamescreen.getGameReference().incrementAchievement("hunter.superpowered.ExtraLife");
		}
		if (item.equals("Invulnerability")){
			invulnerable = true;
			buffTime = System.currentTimeMillis();
			gamescreen.getGameReference().incrementAchievement("hunter.superpowered.Invulnerability");
		}
		if (item.equals("Coin")){
			score += 500;
			gamescreen.getGameReference().incrementAchievement("hunter.superpowered.Coin");
			gamescreen.getGameReference().incrementAchievement("hunter.10Coins");
		}
	}

    /**
     * Adds an animal to animalsKilled
     */
    public void addAnimalKilled() {
        animalsKilled++;
        gamescreen.getGameReference().incrementAchievement("hunter.Hitman");
    }

	/*
	 *
	 * Modifiers
	 * 
	 */

    /**
     * @param score - Integer to be added to the player's score
     */
    public void addScore(int score) {
        this.score += score * multiplier;
    }

    /**
     * Reduces the players life by 1;
     */
    public void loseLife() {
        lives -= 1;
    }

    /**
     * Adds an extra life for the player by 1;
     */
    public void addLife() {
        lives += 1;
    }

    /**
     * @return Boolean of whether the player is dead
     */
    public boolean isDead() {
        return dead;
    }

	/*
	 * 
	 * Boolean checking conditions 
	 * 
	 */

    /**
     * Checks if the player is invulnerable
     *
     * @return Boolean if the player is invulnerable
     */
    public boolean isInvulnerable() {
        return invulnerable;
    }

    /**
     * Returns whether the player is grounded
     *
     * @return grounded
     */
    public boolean isGrounded() {
        return collider.bottom;
    }

    /**
     * Sets whether the player is grounded
     *
     * @param grounded Boolean value of whether its grounded
     */
    public void setGrounded(boolean grounded) {
        this.collider.bottom = grounded;
    }
		
	/*
	 * 
	 * The Getters and Setters
	 * 
	 */

    /**
     * Returns the multiplier
     *
     * @return int - multiplier
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * Sets a score multiplier for the player
     *
     * @param multi - int of multiplier
     */
    public void setMultiplier(int multi) {
        multiplier = multi;
    }

    /**
     * Returns the lives of the player
     *
     * @return int - lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Returns the player's score
     *
     * @return int - score
     */
    public int getCurrentScore() {
        return score;
    }

    /**
     * Returns the amount of animals killed
     *
     * @return int - animalsKilled
     */
    public int getAnimalsKilled() {
        return animalsKilled;
    }

    /**
     * Returns the current distance of the player
     *
     * @return float - distance
     */
    public float getCurrentDistance() {
        return getX() / Config.TILE_SIZE;
    }

    /**
     * @return String - Weapon that is currently used by the player
     */
    public String getWeapon() {
        return Weapon;
    }

    /**
     * Sets the weapon
     *
     * @param s String
     */
    public void setWeapon(String s) {
        Weapon = s;
    }

    /**
     * @return Long - Time of the last attack
     */
    public long getAttackTime() {
        return attackTime;
    }

    /**
     * @return Integer of the ammo remaining on the current weapon, if the weapon is default then 0 is returned
     */
    public int getWeaponAmmo() {
        if (!Weapon.equals("KnifeandFork")) {
            return weaponAmmo;
        } else {
            return 0;
        }
    }

    @Override
    public String getType() {
        return classType;
    }

    /**
     * Sets if the player is invulnerable;
     *
     * @param inv - Boolean of invulnerability
     */
    public void setInvulnerability(boolean inv) {
        invulnerable = inv;
    }

    /**
     * Gets the jump velocity
     *
     * @return float value of the JumpVelocity
     */
    public float getJumpVelocity() {
        return Hunter.State.playerVelocity.y;
    }

    /**
     * Sets the jump velocity
     *
     * @param jumpVelocity Float value of what the jump velocity will be set to
     */
    public void setJumpVelocity(float jumpVelocity) {
        Hunter.State.playerVelocity.y = jumpVelocity;
    }

    /**
     * Returns the Player State
     *
     * @return State
     */
    public State getState() {
        return this.state;
    }

    /**
     * Returns the current animation
     *
     * @return Animation - Current Animation
     */
    public Animation getAnimation() {
        return currAnim;
    }

    /**
     * Return the hurt Animation
     *
     * @return Animation - Hurt Animation
     */
    public Animation hurtAnimation() {
        return animationList.get(Weapon + "damage");
    }
	
	/*
	 * 
	 * Animation Loaders
	 * 
	 */

    /**
     * Returns the death Animation
     *
     * @return Animation - Death animation
     */
    public Animation deathAnimation() {
        return animationList.get(Weapon + "death");
    }

    /**
     * Returns the attack animation
     *
     * @return Animation - Attack Animation
     */
    public Animation attackAnimation() {
        return animationList.get(Weapon + "attack");
    }

    /**
     * Returns the jump animation
     *
     * @return Animation - Jump Animation
     */
    public Animation jumpAnimation() {
        return animationList.get(Weapon + "jump");
    }

    /**
     * Returns the fall animation
     *
     * @return Animation - Fall Animation
     */
    public Animation fallAnimation() {
        return animationList.get(Weapon + "fall");
    }

    /**
     * Returns the damage animation
     *
     * @return Animation - Damage Animation
     */
    public Animation damageAnimation() {
        return animationList.get(Weapon + "damage");
    }

    /**
     * Returns the run animation
     *
     * @return Animation - Run animation
     */
    public Animation runAnimation() {
        return animationList.get(Weapon + "running");
    }

    /**
     * To be called when the GameScreen is disposed
     */
    public void dispose() {
        pickup.dispose();
        death.dispose();
        hurt.dispose();
    }
}