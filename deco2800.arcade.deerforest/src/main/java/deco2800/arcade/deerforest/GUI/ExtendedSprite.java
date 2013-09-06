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
    private AbstractCard card;
    private MainGame game;
	
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
        game = DeerForestSingletonGetter.getDeerForest().mainGame;
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

        //Grey out card if it has attacked
        if(this.getCard() != null && this.getCard() instanceof AbstractMonster) {
            if(this.hasAttacked() && game.getPhase().equals("BattlePhase")) {
                this.setColor(0.5f, 0.5f, 0.5f, 1.0f);
            } if(this.hasAttacked() && game.getPhase().equals("EndPhase")) {
                this.setColor(Color.WHITE);
            }
        }

        super.draw(batch);
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
	
}
