package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Buttons {
	private SpriteBatch batch;
	private BitmapFont font;
	private long xPos;
	private long yPos;
	private final int BUTTONWIDTH = 100;
	private final int BUTTONHEIGHT = 50;
	private boolean isDisplayed;
	private ArrayList<ButtonSingle> buttons =  new ArrayList<ButtonSingle>();
	
	public Buttons() {
		xPos = 0;
		yPos = 0;
		isDisplayed = false;
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		/*ButtonSingle a = new ButtonSingle(1, 1, "Quit", Color.RED, yPos, xPos, BUTTONWIDTH, BUTTONHEIGHT);
		buttons.add(a);
		ButtonSingle b = new ButtonSingle(2, 2, "Replay", Color.RED, yPos - BUTTONHEIGHT, xPos, BUTTONWIDTH, BUTTONHEIGHT);
		buttons.add(b);*/
	}
	
	public void AddButtonsFromList(ArrayList<String> listToAddFrom) {
		int i = 1;
		for (String y: listToAddFrom) {
			ButtonSingle a = new ButtonSingle(i, 1, y, Color.RED, yPos - (BUTTONHEIGHT * (i-1)), xPos, BUTTONWIDTH, BUTTONHEIGHT);
			buttons.add(a);
			i++;
		}
	}
	
	public void display(){
		isDisplayed = true;
	}
	
	public void hide(){
		isDisplayed = false;
	}
	
	public void setX(long xSet){
		xPos = xSet - BUTTONWIDTH;
		for (ButtonSingle x: buttons) {
			x.setXPos(xPos);
		}
	}
	
	public void setY(long ySet){
		yPos = ySet;
		for (ButtonSingle x: buttons) {
			x.setYPos(yPos - BUTTONHEIGHT * (x.buttonNo - 1) );
		}
	}

    public void render()
    {
    	if (isDisplayed) {
    		batch.begin();
    		for (ButtonSingle x: buttons){
    			font.setColor(x.buttonColor);   			
    			font.draw(batch, x.buttonText, x.GetXPos(), x.GetYPos() );
    		}
    		batch.end();
    	}
    }
    
    public void checkHovered(long mouseX1, long mouseY1){
    	double mouseX = mouseX1 * 1.6;
    	double mouseY = mouseY1 * 1.6;
    	
    	for (ButtonSingle x: buttons){
    		x.CheckHovered(mouseX, mouseY);
    	}
    }
    
    public int checkButtonsPressed(long mouseX1, long mouseY1){
    	double mouseX = mouseX1 * 1.6;
    	double mouseY = mouseY1 * 1.6;
    	
    	for (ButtonSingle x: buttons) {
    		if (x.CheckPressed(mouseX, mouseY)) {
    			return x.buttonNo;
    		}
    	}
    	
    	return 0;
    }	
}
