package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.CardCollectionList;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

/**
 * Subclass of the Sprite class for the deck builder.
 */
public class BuilderSprite extends Sprite {
	
	private Rectangle originZone;
	private boolean deck;
	private boolean cardBool;
	private String area;
    private boolean isSelected;
    private AbstractCard card;
    private DeckBuilder game;
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
     * Initializes BuilderSpite
     * @param t Texture
     */
	public BuilderSprite(Texture t) {
		super(t);
		setOrigin(0,0);
		this.flip(false, true);
		originZone = null;
		setArea(null);
        this.card = null;
        this.isSelected = false;
        game = DeerForest.deckBuilder;

        this.deltaX = 0;
        this.deltaY = 0;
        this.timeToMove = 0;
        this.currentMoveTime = 0;
	}
	
	/**
	 * Initializes BuilderSprite from another BuilderSprite
	 * @param s
	 */
	public BuilderSprite(BuilderSprite s) {
        super(s.getTexture());
        setOrigin(0, 0);
        this.flip(false, true);
        originZone = s.getOriginZone();
        setArea(s.getArea());
        this.card = s.getCard();
        this.setPosition(s.getX(), s.getY());
        game = DeerForestSingletonGetter.getDeerForest().deckBuilder;
    }
	
	/**
	 * Checks whether bounding rectangle contains the point
	 *  
	 * @param x
	 * @param y
	 * @return true if point is contained
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
	 * Sets the card
	 * @param c card
	 */
	public void setCard(AbstractCard c) {
        this.card = c;
    }
	
	/**
	 * gets the card
	 * @return AbstractCard
	 */
    public AbstractCard getCard() {
        return this.card;
    }
    
    /**
     * Gets the origin zone
     * @return Rectangle
     */
    public Rectangle getOriginZone() {
		return originZone;
	}
	
    /**
     * Sets the origin zone
     * @param r 
     */
	public void setOriginZone(Rectangle r) {
		originZone = r;
	}
	
	/**
	 * Gets the area
	 * @return the area string name
	 */
	public String getArea() {
		return area;
	}
	
	/**
	 * Sets the area 
	 * 
	 * @param area String
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * Sets wether or not the card is selected
	 * @param b
	 */
    public void setSelected(boolean b) {
        this.isSelected = b;
    }
	
    /**
     * Checks wether card is deck 
     * @return treu if deck
     */
    public boolean isDeck() {
		return deck;
	}

    /**
     * Set card to be or not be deck
     * @param deck
     */
	public void setDeck(boolean deck) {
		this.deck = deck;
	}

	/**
	 * Sees if selection is card
	 * @return true if is card
	 */
	public boolean isCard() {
		return cardBool;
	}
	
	/**
	 * Set card to be or not be card
	 * @param cardBool
	 */
	public void setCard(boolean cardBool) {
		this.cardBool = cardBool;
	}
    
	/**
	 * Moves card
	 * @param x
	 * @param y
	 * @param time
	 */
	public void moveTo(float x, float y, int time) {
	        this.deltaX = x - this.getX();
	        this.deltaY = y - this.getY();
	        this.timeToMove = time;
	        this.currentMoveTime = 0;
	    }
	
	/**
	 * Scales card
	 * @param xScale
	 * @param yScale
	 * @param time
	 */
	public void scaleTo(float xScale, float yScale, int time) {
	        this.scaleDeltaX = xScale - this.getScaleX();
	        this.scaleDeltaY = yScale - this.getScaleY();
	        this.scaleTimeToMove = time;
	        this.scaleCurrentMoveTime = 0;
	}
}