package zombieInvaders;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//Need to do up class Sprite
public class tank extends Sprite implements Stats{
    public final int START_Y = 300;
    public final int START_X = 300;
    private final String player = "AngryRedBird.png";
    private int iconWidth;
    
    public Tank(){


    	ImageIcon playerImage = new ImageIcon(this.getClass().getResource(player));
    	iconWidth = playerImage.getImage().getWidth(null);
    	
    	setImage(playerImage.getImage());
    	setX(START_X);
    	setY(START_Y);
    }
}
