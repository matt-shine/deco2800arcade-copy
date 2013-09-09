package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.Color;

public class ButtonSingle {
    public int buttonNo;
	public String buttonText;
	public Color buttonColor;
	public long buttonYPos;
	    	
	public ButtonSingle(int buttonNo, String buttonText, Color buttonColor, long buttonYPos){
		this.buttonNo = buttonNo;
	    this.buttonText = buttonText;
	    this.buttonColor = buttonColor;
	    this.buttonYPos = buttonYPos;
	}
}
