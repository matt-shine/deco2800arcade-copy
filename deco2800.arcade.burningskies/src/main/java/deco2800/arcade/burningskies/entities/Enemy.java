package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.burningskies.screen.PlayScreen;


public class Enemy extends Ship {

	private float xPos;
	private float yPos;
	
	//test parameters
	private float xMin = 50;
	private float xMax = 1050;
	private float velocity = 400;
	
	
	public Enemy(int health, Texture image, Vector2 pos, PlayScreen screen) {
		super(health, image, pos);

		xPos = pos.x;
		yPos = pos.y;

		// resizing the enemy unit to 1/3 of the original
		this.setHeight((float) 100);
		this.setWidth((float) 100);
	}
	
	public void onRender(float delta) {
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
	}
}
