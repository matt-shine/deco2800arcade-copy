package Invaders;
import java.awt.Color;
import java.awt.Graphics;

public class tankshot {

	private int p_x;
	private int p_y;

	public tankshot(int x, int y) {
		p_x = x;
		p_y = y;

	}
	
	public void drawshot(Graphics g){
		
		g.setColor(Color.green);
		
		g.fillRect(p_x+20, p_y,5,10);
	}
	
	public void Update(){
		p_y -=7;
	}
	
	public int positionX(){
		return p_x;
	}
	public int positionY(){
		return p_y;
	}

}
