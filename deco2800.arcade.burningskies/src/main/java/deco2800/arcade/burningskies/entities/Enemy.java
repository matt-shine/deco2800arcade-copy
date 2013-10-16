package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class Enemy extends Ship {

	private float xPos;
	private float yPos;
	
	//test parameters
	//private float xMin = 50;
	//private float xMax = 1050;
	//private float velocity = 400;
	
	// parameters to control direction and speed
	//private float speed;
	private Vector2 direction;
	
	public Enemy(int health, Texture image, Vector2 pos, PlayScreen screen) {
		super(health, image, pos);

		xPos = pos.x;
		yPos = pos.y;
		setWidth(getWidth()/3);
		setHeight(getHeight()/3);
		setPosition(xPos, yPos);
	}
	
	public Enemy(int health, Texture image, Vector2 pos, PlayScreen screen, Vector2 dir) {
		super(health, image, pos);

		xPos = pos.x;
		yPos = pos.y;
		
		this.direction = dir;
//		this.speed = speed;
		
		setWidth(getWidth()/3);
		setHeight(getHeight()/3);
		setPosition(xPos, yPos);
	}
	
	public void onRender(float delta) {
		super.onRender(delta);
		/*
		if(xPos < xMax && velocity > 0) { // check to make sure it can still move right
			xPos += (float) velocity*delta;
			if(xPos >= xMax)
				velocity *= -1;
		}
		else if(xPos > xMin && velocity < 0) { // check to make sure it can still move left
			xPos += (float) velocity*delta;
			if(xPos <= xMin)
				velocity *= -1;
		}		
		
		this.setPosition(xPos, yPos);
		*/
		linear(delta);
	}
	
	private void linear(float delta) {		
		xPos += (float) direction.x * delta;
		yPos += (float) direction.y * delta;
				
		this.setPosition(xPos, yPos);		
	}
}
