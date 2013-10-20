package deco2800.arcade.raiden;
import java.awt.BorderLayout;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	
	private GamePanel gamePanel = new GamePanel();
	public static int score;
	public GameFrame(){
		//set the size and game frame.
		this.setTitle("Raiden");
		this.setSize(Global.FRAME_WIDTH + 10, Global.FRAME_HEIGHT + 35);
		this.setLocationRelativeTo(null);
		gamePanel.setSize(Global.FRAME_WIDTH, Global.FRAME_HEIGHT);
		this.add(gamePanel,BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		gamePanel.requestFocus();
		//Retrieve the score for high score.
		score = gamePanel.getScore();
	}
	
}
