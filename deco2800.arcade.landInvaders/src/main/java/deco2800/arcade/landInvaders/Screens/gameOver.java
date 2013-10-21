package deco2800.arcade.landInvaders.Screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.landInvaders.Invaders;

public class gameOver extends JFrame{
	private JPanel p = new JPanel();
	private JLabel t = new JLabel();
	private JButton next = new JButton();
	private JFrame s = this;
	private Invaders i;
	private HighscoreClient player;
 
	public gameOver(int score,HighscoreClient player){
		super("Game Over");
		this.add(p);
		this.player =player;
		p.add(t);
		t.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/GOver.png"))).getImage()).getScaledInstance(
						800, 500, java.awt.Image.SCALE_SMOOTH)));
		t.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(100, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;

		next.setOpaque(false);
		next.setContentAreaFilled(false);
		next.setBorderPainted(false);
		next.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button13.png")))
				.getImage()).getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		next.addMouseListener(new mouse1());
		t.add(next, gbc);
		
JLabel l = new JLabel();
		
		GridBagConstraints gbd = new GridBagConstraints();
		gbd.anchor = GridBagConstraints.WEST;
		gbd.insets = new Insets(0,150,60,0);
		gbd.gridx = 0;
		gbd.gridy = 0;
		t.add(l,gbd);
		l.setForeground(Color.black);
		l.setFont(new Font("Algerian",1, 40));
		l.setText(score + "");
		
		this.setVisible(true);
		this.setSize(800,530);
		
		

	}
	

	public class mouse1 implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			next.setIcon(new ImageIcon(
					((new ImageIcon(this.getClass().getResource("/image/Button14.png"))).getImage())
					.getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Invaders i = new Invaders(player);
			s.dispose();

		}

		@Override
		public void mouseExited(MouseEvent e) {
			next.setIcon(new ImageIcon(
					((new ImageIcon(this.getClass().getResource("/image/Button13.png"))).getImage())
					.getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}


	


	


}