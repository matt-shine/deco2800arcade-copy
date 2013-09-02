package deco2800.arcade.junglejump;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Player class representing player in Jungle Jump Game
 * @author {TEAM-NAME}
 *
 */

public class Player {
	public enum STATE {
		IDLE, WALKING, JUMPING, CLIMBING
	} 
	private float lives = 100f;
	private float coins = 0f;
	static final float SIZE = 0.5f; // Half-unit in the world
	public Body body;
	
	// set default state
	public STATE state = STATE.IDLE;
	
	// Position Array
	private static float posX = 0f;
	private static float posY = 0f;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	boolean facingLeft = true;
	
//	public Player (World world, int startPosX, int startPosY) {
//		PolygonShape boxPoly = new PolygonShape();
//		boxPoly.setAsBox(1f, 5f);
//		BodyDef boxBodyDef = new BodyDef();
//		boxBodyDef.position.x = startPosX;
//		boxBodyDef.position.y = startPosY;
//		Body boxBody = world.createBody(boxBodyDef);
//		
//		boxBody.createFixture(boxPoly, 1);
//		body = boxBody;
//		body.setUserData(this);
//		boxPoly.dispose();
//	}
	
	public Player (Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	
	
	
	
	
	
	
	public float getPosX() {
		return this.posX;
	}
	public float getPosY() {
		return this.posY;
	}
	
	
	
	
}
