package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

public class ExtendedSprite extends Sprite {

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

    //Create a duplicate extended sprite from given extended sprite
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

    //Override the draw method to also draw atk / health
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

        //Draw card attack / health
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

    //Moves the sprite to the point over given time t
    public void moveTo(float x, float y, int time) {
        this.deltaX = x - this.getX();
        this.deltaY = y - this.getY();
        this.timeToMove = time;
        this.currentMoveTime = 0;
    }

    public void scaleTo(float xScale, float yScale, int time) {
        this.scaleDeltaX = xScale - this.getScaleX();
        this.scaleDeltaY = yScale - this.getScaleY();
        this.scaleTimeToMove = time;
        this.scaleCurrentMoveTime = 0;
    }

    public void setCard(AbstractCard c) {
        this.card = c;
    }

    public AbstractCard getCard() {
        return this.card;
    }

    public void setHasAttacked(boolean b) {
        this.hasAttacked = b;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

	public Rectangle getOriginZone() {
		return originZone;
	}
	
	public void setOriginZone(Rectangle r) {
		originZone = r;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public boolean isField() {
		return field;
	}

	public void setField(boolean field) {
		this.field = field;
	}

	public boolean isMonster() {
		return monster;
	}

	public void setMonster(boolean monster) {
		this.monster = monster;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

    public void setSelected(boolean b) {
        this.isSelected = b;
    }
	
}
