package Invaders;
import java.awt.Color;
import java.awt.Graphics;


public class enemy {
	private int position_x;
	private int position_y;
	private int width;
	private int height;

	
public enemy(int Px,int Py,int SizeW, int SizeH){
	position_x = Px;
	position_y = Py;
	width = SizeW;
	height=SizeH;
}

public void drawEnemy(Graphics g,int move){
	g.setColor(Color.red);
	g.fillRect(position_x+move, position_y, width, height);
	
	
}
	




}
