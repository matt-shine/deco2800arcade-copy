package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author li.tang
 * This is the class to store the snake or ladder object
 */
public class SnakeLadderBridge {
	
	private Sprite sprite;
	private Texture texture;
	private int destinationPosition;
	private int originPosition;
	private boolean isLadder;
	
	public SnakeLadderBridge(int originPosition,int destinationPosition,boolean isLadder)
	{
		this.isLadder = isLadder;
		this.destinationPosition = destinationPosition;
		this.originPosition = originPosition;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * This method initialize a ladder or a snake including scaling and rotation angle.
	 * @param tileList a full list of tiles in the map
	 */
	public void createSnakeLadder(Tile[] tileList)
	{
		String type = this.isLadder?"ladder":"snake";
		this.texture = new Texture(Gdx.files.classpath("images/"+type+".png"));
		this.sprite = new Sprite(texture); 
		Vector2 ladderVector = new Vector2(tileList[destinationPosition-1].getCoorX() - tileList[originPosition-1].getCoorX(), tileList[destinationPosition-1].getCoorY() - tileList[originPosition-1].getCoorY());
		sprite.setBounds(tileList[originPosition-1].getCoorX()+Tile.getDimension()/2, tileList[originPosition-1].getCoorY()+Tile.getDimension()/2, 30, ladderVector.len());
		sprite.setOrigin(0f,0f);
		sprite.setRotation(ladderVector.angle()-90f);
	}

}
