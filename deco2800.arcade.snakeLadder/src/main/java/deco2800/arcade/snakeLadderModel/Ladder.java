package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Ladder {
	
	private Sprite sprite;
	private Texture texture;
	private int destinationPosition;
	private int originPosition;
	
	public Ladder(int originPosition,int destinationPosition)
	{
		this.texture = new Texture(Gdx.files.classpath("images/arrow.png"));
		this.sprite = new Sprite(texture); 
		this.destinationPosition = destinationPosition;
		this.originPosition = originPosition;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void createLadder(Tile[] tileList)
	{
		Vector2 ladderVector = new Vector2(tileList[destinationPosition-1].getCoorX() - tileList[originPosition-1].getCoorX(), tileList[destinationPosition-1].getCoorY() - tileList[originPosition-1].getCoorY());
		sprite.setBounds(tileList[originPosition-1].getCoorX()+Tile.getDimension()/2, tileList[originPosition-1].getCoorY()+Tile.getDimension()/2, 30, ladderVector.len());
		sprite.setOrigin(0f,0f);
		sprite.setRotation(ladderVector.angle()-90f);
	}

}
