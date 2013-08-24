package main.java.deco2800.arcade.junglejump;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
	public enum STATE {
		IDLE, UP, LEFT, RIGHT, DOWN
	} 
	private float lives = 100f;
	private float coins = 0f;
	public Body body;
	
	// set default state
	public STATE state = STATE.IDLE;
	
	// Position Array
	private static float posX = 0f;
	private static float posY = 0f;
	
	
	public float getPosX() {
		return this.posX;
	}
	public float getPosY() {
		return this.posY;
	}
	
	
	
	
}
