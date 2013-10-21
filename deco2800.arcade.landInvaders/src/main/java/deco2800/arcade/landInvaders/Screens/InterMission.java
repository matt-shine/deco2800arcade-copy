package deco2800.arcade.landInvaders.Screens;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class InterMission extends JFrame{

	static JPanel stageClear = new JPanel();
	static JPanel game = new JPanel();
	static JButton continueBtn = new JButton();
	static JButton test = new JButton();
	static JFrame s;

	
		public InterMission(){
			super("StageCleared!");
			s =this;
		this.setVisible(true);
		this.setSize(800,500);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(stageClear);
		
		JLabel highscore = new JLabel("High Score: ");

		stageClear.add(highscore);
		stageClear.add(continueBtn);
		continueBtn.addActionListener(new btnClick());
		test.addActionListener( new tests());

		//Sets continue button to be transparent with image
		continueBtn.setOpaque(false);
		continueBtn.setContentAreaFilled(false);
		continueBtn.setBorderPainted(false);
		continueBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/NextBtn.png"))).getImage()).getScaledInstance(400, 100, java.awt.Image.SCALE_SMOOTH)));

		}
		static class btnClick implements ActionListener {
			public  btnClick() {

				
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stageClear.removeAll();
				stageClear.add(game);
				stageClear.revalidate();
				stageClear.repaint();
				JLabel label = new JLabel ("suppose to continue next game");
			
				game.add(label);

				
			}
		}
		
		static class tests implements ActionListener {
			public  tests() {

				
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				

				
			}
			
		}
		
		
		
	}


	
	


