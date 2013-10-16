package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovablePlatform extends MovableEntity{
	private Texture texture;
	private Rectangle collRect;
	private float xOrigin;
	private float yOrigin;
	private boolean rebound;
	private Vector2 targetPos;
	private Vector2 initialPos;
	private float wait;
	private Vector2 positionDelta;
	
	private float waitCount;
	private boolean firstFrameOfWait;
	
	public MovablePlatform(MovablePlatform platform) {
		this(platform.getTexture(), new Vector2(platform.getPosition().x, platform.getPosition().y), platform.getWidth(), platform.getHeight(),
				new Vector2(platform.getTargetPosition().x, platform.getTargetPosition().y), platform.getSpeed(), platform.getRebound(), 
				platform.getWait());
	}
	
	public MovablePlatform(Texture texture, Vector2 pos, float width, float height, float speed) {
		this(texture, pos, width, height, pos, speed, false, 0);
	}
	
	public MovablePlatform(Texture texture, Vector2 pos, float width, float height, Vector2 targetPos, float speed, boolean rebound, float wait) {
		super(speed, 0, pos, width, height);
		this.texture = texture;
		collRect = new Rectangle (pos.x, pos.y, width, height);
		xOrigin = 0;
		yOrigin = 0;
		this.rebound = rebound;
		this.targetPos = targetPos;
		this.initialPos = new Vector2(pos.x, pos.y);
		waitCount = 0f;
		positionDelta = new Vector2(0,0);
		this.wait = wait;
		firstFrameOfWait = true;
	}
	
	public Texture getTexture() {
		return texture;
	}
	public boolean isMoving() {
		if (positionDelta.x == 0 && positionDelta.y == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getRebound() {
		return rebound;
	}
	
	public float getWait() {
		return wait;
	}
	public Vector2 getTargetPosition() {
		return targetPos;
	}
	public void setTargetPosition(Vector2 targetPos) {
		this.targetPos = targetPos;
	}
	public void setTargetPositionToPosition() {
		targetPos = position;
	}
	public Rectangle getCollisionRectangle() {
		return collRect;
	}
	
	public Vector2 getPositionDelta() {
		return positionDelta;
	}
	
	public void setCollisionRectangle(float xOrigin, float yOrigin, float width, float height) {
		collRect.set( new Rectangle(getPosition().x + xOrigin, getPosition().y + yOrigin,width,height) );
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
	}
	
	@Override
	public void update(Player ship) {
		super.update(ship);
		float delta = Gdx.graphics.getDeltaTime();
		
		int inPosition = 0;
		//the following function needs editing and might actually need to go after the calculation of position
		if (position.x <= targetPos.x + delta*speed+0.1f && position.x >= targetPos.x - delta*speed -0.1f) {
			position.x = targetPos.x;
			//System.out.println("In x position");
			inPosition++;
		}
		if (position.y <= targetPos.y + delta*speed+0.1f && position.y >= targetPos.y - delta*speed-0.1f) {
			position.y = targetPos.y;
			//System.out.println("In y position");
			inPosition++;
		}
		
		if (inPosition == 2) {
			//If platform is within the range of the target, stop moving
			//System.out.println("In position");
			if (firstFrameOfWait) {
				//System.out.println("@@@@First Frame@@@@@");
				firstFrameOfWait = false;
			} else {
				//System.out.println("@@@@Stop Vector@@@@");
			positionDelta = new Vector2(0,0);
			}
			if (rebound) {
				waitCount += delta;
				//System.out.println(waitCount);
				if (waitCount > wait) {
					//System.out.println("Rebound time. positiondump:"+targetPos.x+targetPos.y+initialPos.x+initialPos.y+" waitCount: "+waitCount+" wait: "+wait);
					float tmpX = targetPos.x;
					float tmpY = targetPos.y;
					targetPos.x = initialPos.x;
					targetPos.y = initialPos.y;
					initialPos.x = tmpX;
					initialPos.y = tmpY;
					waitCount = 0f;
					firstFrameOfWait = true;
					//System.out.println("After. positiondump:"+targetPos.x+targetPos.y+initialPos.x+initialPos.y);
				}
			}
			
		} else {
			//Move towards the target position
			Vector2 oldPos = new Vector2(position.x, position.y);
			Vector2 dir = new Vector2(new Vector2(targetPos.x,targetPos.y).sub(
					new Vector2(position.x, position.y)));
			dir.nor().mul(delta * speed);
			position.add(dir);
			positionDelta = new Vector2(position.x, position.y).sub(new Vector2(oldPos.x, oldPos.y));
			//System.out.println("Moved by "+ positionDelta.x+","+positionDelta.y);
		}
		
		
		//Update collision rectangle to be in the correct place
		collRect.x = getPosition().x + xOrigin;
		collRect.y = getPosition().y + yOrigin;
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
