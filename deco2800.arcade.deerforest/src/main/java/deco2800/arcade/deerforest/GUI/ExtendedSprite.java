package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

/**
 * A representation of a sprite specific to the main game. This sprite stores
 * its own data (attack, picture file path, defense, etc...)
 */
public class ExtendedSprite extends Sprite {

    //Variables for the sprites own knowledge of itself and the game
	private Rectangle originZone;
	private int player;
	private boolean field;
	private boolean monster;
	private String area;
    private boolean hasAttacked;
    private boolean isSelected;
    private AbstractCard card;
    private MainGame game;
    private float glow;
    private boolean glowDirection;

    //Variables for moving sprite
    private float deltaX;
    private float deltaY;
    private float timeToMove;
    private float currentMoveTime;

    //Variables for scaling sprite
    private float scaleDeltaX;
    private float scaleDeltaY;
    private float scaleTimeToMove;
    private float scaleCurrentMoveTime;

    /**
     * Initialises the ExtendedSprite, setting the correct selection settings
     * and the movement settings
     * @param t the texture the extended sprite uses when drawing itself
     */
	public ExtendedSprite(Texture t) {
		super(t);
		setOrigin(0,0);
		this.flip(false, true);
		originZone = null;
		setPlayer(0);
		setField(false);
		setMonster(false);
		setArea(null);
        this.hasAttacked = false;
        this.card = null;
        this.isSelected = false;
        glow = 0;
        glowDirection = true;
        game = DeerForestSingletonGetter.getDeerForest().mainGame;

        this.deltaX = 0;
        this.deltaY = 0;
        this.timeToMove = 0;
        this.currentMoveTime = 0;
	}

    /**
     * Create a duplicate extended sprite from given extended sprite, note that
     * it is a shallow copy, that is the texture used for the given sprite is
     * the same as the one used for the copy
     * @param s the extended sprite to duplicate
     */
    public ExtendedSprite(ExtendedSprite s) {
        super(s.getTexture());
        setOrigin(0, 0);
        this.flip(false, true);
        originZone = s.getOriginZone();
        setPlayer(s.getPlayer());
        setField(s.isField());
        setMonster(s.isMonster());
        setArea(s.getArea());
        this.hasAttacked = s.hasAttacked;
        this.card = s.getCard();
        this.setPosition(s.getX(), s.getY());
        game = DeerForestSingletonGetter.getDeerForest().mainGame;
    }

    /**
     * Tests whether the sprite currently contains the given x,y point
     * @param x the x part of the point
     * @param y the y part of the point
     * @return true is the sprite contains the point, false otherwise
     */
	public boolean containsPoint(int x, int y) {
		
		Rectangle r = this.getBoundingRectangle();
		
		double leftSide = r.getX();
		double rightSide = r.getX() + r.getWidth();
		double topSide = r.getY();
		double bottomSide = r.getY() + r.getHeight();
		
		if(x > leftSide && x < rightSide && y > topSide && y < bottomSide) {
			return true;
		}
		return false;
	}

    /**
     * Override the draw method to also draw atk / health as well as glowing
     * if the sprite is defined as being 'selected'
     * @param batch the batch to draw the sprite with
     */
    @Override
    public void draw(SpriteBatch batch) {
        //Make color selected card (if it is a card
        if(this.getCard() != null) {
            if(this.isSelected) {
                this.setColor(1.0f-glow, 1.0f-glow, 1.0f-glow, 1.0f);
                glow = glowDirection?glow+0.01f:glow-0.01f;
            } else {
                this.setColor(Color.WHITE);
                glow = 0;
                glowDirection = true;
            }

            //Adjust glow direction
            if(glow >= 0.5f) glowDirection = false;
            else if(glow <= 0) glowDirection = true;
        }

        //Grey out card if it has attacked and is not selected
        if(this.getCard() != null && this.isSelected == false && this.getCard() instanceof AbstractMonster) {
            if(this.hasAttacked() && game.getPhase().equals("BattlePhase")) {
                this.setColor(0.5f, 0.5f, 0.5f, 1.0f);
            } if(this.hasAttacked() && game.getPhase().equals("EndPhase")) {
                this.setColor(Color.WHITE);
            }
        }

        super.draw(batch);

        //Draw the stats of the monster
        drawStats(batch);

        //Update moving to point
        if(currentMoveTime < timeToMove) {
            this.setPosition(this.getX()+deltaX/timeToMove, this.getY()+deltaY/timeToMove);
            currentMoveTime++;
        }

        //Update scaling
        if(scaleCurrentMoveTime < scaleTimeToMove) {
            this.setScale(this.getScaleX()+scaleDeltaX/scaleTimeToMove, this.getScaleY()+scaleDeltaY/scaleTimeToMove);
            scaleCurrentMoveTime++;
        }
    }

    /**
     * Draws the sprites stats if it is an instance of a monster card
     * @param batch
     */
    private void drawStats(SpriteBatch batch) {
        if(this.card instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster) this.getCard();
            Rectangle r = this.getBoundingRectangle();
            float x = r.getX() + 39*r.getWidth()/50;
            float y = r.getY() + r.getHeight()/2;

            game.font.setScale(this.getScaleX()*1.1f);
            game.font.setColor(this.getColor());
            if(String.valueOf(m.getAttack()).length() == 3) {
                game.font.draw(batch, String.valueOf(m.getAttack()), x, y);
            } else {
                game.font.draw(batch, String.valueOf(m.getAttack()), x + r.getWidth()/20, y);
            }

            if(String.valueOf(m.getCurrentHealth()).length() == 3) {
                game.font.draw(batch, String.valueOf(m.getCurrentHealth()), x, y+r.getHeight()/5);
            } else {
                game.font.draw(batch, String.valueOf(m.getCurrentHealth()), x+r.getWidth()/20, y+r.getHeight()/5);
            }

            game.font.setColor(Color.WHITE);
            game.font.setScale(0.5f);
        }
    }

    /**
     * Moves the sprite to the point x,y over given time t
     * @param x the x point to move the sprite to
     * @param y the y point to move the sprite to
     * @param t how long the sprite should take to move to the point
     */
    public void moveTo(float x, float y, int t) {
        this.deltaX = x - this.getX();
        this.deltaY = y - this.getY();
        this.timeToMove = t;
        this.currentMoveTime = 0;
    }

    /**
     * Scales the sprite to the given scale over time t
     * @param xScale the xScale to scale the sprite to
     * @param yScale the yScale point to scale the sprite to
     * @param t how long the sprite should take to scale
     */
    public void scaleTo(float xScale, float yScale, int t) {
        this.scaleDeltaX = xScale - this.getScaleX();
        this.scaleDeltaY = yScale - this.getScaleY();
        this.scaleTimeToMove = t;
        this.scaleCurrentMoveTime = 0;
    }

    /**
     * Getters and Setters for all variables that people may have to use
     * when dealing with extended sprites
     */

    public void setCard(AbstractCard c) {
        this.card = c;
    }

    /**
     * Returns the underlying card.
     */
    public AbstractCard getCard() {
        return this.card;
    }

    /**
     * Sets whether the sprite has attacked or not.
     */
    public void setHasAttacked(boolean b) {
        this.hasAttacked = b;
    }

    /**
     * Returns true if the sprite has attacked and false otherwise.
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Returns the orgin zone of the sprite.
     */
	public Rectangle getOriginZone() {
		return originZone;
	}
	
	/**
	 * Sets the origin zone of the sprite.
	 */
	public void setOriginZone(Rectangle r) {
		originZone = r;
	}

	/**
	 * Returns the player.
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Sets the player
	 */
	public void setPlayer(int player) {
		this.player = player;
	}

	/**
	 * Returns true if the sprite is a field or false otherwise.
	 */
	public boolean isField() {
		return field;
	}

	/**
	 * Sets the field.
	 */
	public void setField(boolean field) {
		this.field = field;
	}

	/**
	 * Returns true if the sprite is a monster or false otherwise.
	 */
	public boolean isMonster() {
		return monster;
	}

	/**
	 * Sets the monster for the sprite
	 */
	public void setMonster(boolean monster) {
		this.monster = monster;
	}

	/**
	 * Returns the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * Sets the area
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * Sets the selected sprite
	 */
    public void setSelected(boolean b) {
        this.isSelected = b;
    }
	
}
