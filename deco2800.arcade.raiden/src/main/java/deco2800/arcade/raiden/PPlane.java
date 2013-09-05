package deco2800.arcade.raiden;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class PPlane extends Plane{
	private int direction;
	public static boolean UP;
	public static boolean DOWN;
	public static boolean LEFT;
	public static boolean RIGHT;
	public static int life = 100;
	public static boolean isFired;
	private Texture Plane;
	public PPlane(){
		
	}
	public PPlane(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	

	public void drawMe(SpriteBatch batch) {
		// TODO Auto-generated method stub
		Plane = new Texture(Gdx.files.classpath("resources/Boss1.png"));
		batch.draw(Plane, x, y);
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void changeDirection(int direction){
		this.direction = direction;
	}
	
	public void pplaneMove(){
		if (UP)
			y -= Global.SPEED;
		if (DOWN)
			y += Global.SPEED;
		if (LEFT)
			x -= Global.SPEED;
		if (RIGHT)
			x += Global.SPEED;
	}
	
	public boolean isAlive(){
		if(PPlane.life <= 0){
			PPlane.life = 0;
			return false;    
		} 
		else
			return true;
	}
}
