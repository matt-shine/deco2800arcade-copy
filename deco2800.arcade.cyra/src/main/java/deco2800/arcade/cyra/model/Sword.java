package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Sword extends MovableEntity {

	private static final float BASE_WIDTH = 0.2f;
	private static final float BASE_HEIGHT = 0.2f;
	private static final float FRAME_LENGTH = 0.10f;
	
	private float frame = 0;
	private boolean facingRight = false;
	private boolean inProgress = false;
	
	/**
	 * Intanciate sword.
	 * @param pos
	 */
	public Sword(Vector2 pos) {
		super(0, 0, pos, BASE_WIDTH, BASE_HEIGHT);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Initialize variables
	 * @param facingRight
	 */
	public void begin(boolean facingRight) {
		this.facingRight = facingRight;
		frame = 0;
		inProgress = true;
		
	}
	
	/**
	 * Getter method to find out if currently attacking with sword.
	 * @return
	 */
	public boolean inProgress() {
		return inProgress;
	}
	
	/**
	 * Stop sword attack
	 */
	public void cancel() {
		position = new Vector2 (-1, -1);
		inProgress = false;
	}
	
	/**
	 * Find out the current frame
	 * @return current frame of sword attack.
	 */
	public int getFrame(){
		if(inProgress == true){
			if(frame < FRAME_LENGTH){
				return 1;
			}
			else if(frame < 2f * FRAME_LENGTH){
				return 2;
			}
			else if(frame < 3f * FRAME_LENGTH){
				return 3;
			}
			else{
				return 0;
			}
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Actual sword movement
	 */
	@Override
	public void update(Player ship) {
		super.update(ship);
		
		frame += Gdx.graphics.getDeltaTime();
		
		//1st rectangle
		if (frame < FRAME_LENGTH) {
			setBounds(new Rectangle(position.x, position.y, 0.8f, 0.6f));
			if (facingRight) {
				position = new Vector2(ship.getPosition().x + 0.4f, ship.getPosition().y + ship.getHeight() + 0.12f);
			}
			else {
				position = new Vector2(ship.getPosition().x - getWidth() + ship.getWidth() - 0.4f, ship.getPosition().y +ship.getHeight() + 0.12f);
			}
			
			
		//2nd rectangle	
		} else if (frame < 2f * FRAME_LENGTH) {
			setBounds(new Rectangle(position.x, position.y, 3f, 1.6f));
			if (facingRight) {
				position = new Vector2(ship.getPosition().x + 0.58f, ship.getPosition().y + 0.25f);
			} else {
				position = new Vector2(ship.getPosition().x - getWidth() +ship.getWidth() - 0.58f, ship.getPosition().y + 0.25f);
			}
			
			
		//3rd rectangle
		} else if (frame < 3f * FRAME_LENGTH) {
			setBounds(new Rectangle(position.x, position.y, 0.8f, 0.6f));
			if (facingRight) {
				position = new Vector2(ship.getPosition().x + 0.4f, ship.getPosition().y - 0.25f);
			} else {
				position = new Vector2(ship.getPosition().x - getWidth() + ship.getWidth() - 0.4f, ship.getPosition().y - 0.25f);
			}
			
		
		//completed sword swipe
		} else {
			position = new Vector2 (-1, -1);
			inProgress = false;
			
		}
		
		
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleXCollision(Rectangle tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}

}
