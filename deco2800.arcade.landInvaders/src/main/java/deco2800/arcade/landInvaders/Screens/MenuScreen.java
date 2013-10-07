package deco2800.arcade.landInvaders.Screens;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import deco2800.arcade.landInvaders.Invaders;

public class MenuScreen extends JFrame{
    private JLabel t = new JLabel();
	private JButton instructionBtn = new JButton();
	private JButton newGameBtn = new JButton();

	public MenuScreen(){
		super("LandInvaders");
		this.add(t);
		t.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/stage.jpg"))).getImage()).getScaledInstance(
						800, 500, java.awt.Image.SCALE_SMOOTH)));
		t.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;

		newGameBtn.setOpaque(false);
		newGameBtn.setContentAreaFilled(false);
		newGameBtn.setBorderPainted(false);
		newGameBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button1.png")))
				.getImage()).getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		newGameBtn.addMouseListener(new mouse1());
		t.add(newGameBtn, gbc);
		gbc.gridy++;

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 2, 2, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		instructionBtn.setOpaque(false);
		instructionBtn.setContentAreaFilled(false);
		instructionBtn.setBorderPainted(false);
		instructionBtn.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/Button3.png"))).getImage())
				.getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		instructionBtn.addMouseListener(new mouse2());
		instructionBtn.addActionListener(new btnClick1());
		t.add(instructionBtn, gbc);
		
		this.setVisible(true);
		this.setSize(800,500);

	}

	public class mouse1 implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			newGameBtn.setIcon(new ImageIcon(
					((new ImageIcon(this.getClass().getResource("/image/Button2.png"))).getImage())
					.getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			newGameBtn.setIcon(new ImageIcon(
					((new ImageIcon(this.getClass().getResource("/image/Button1.png"))).getImage())
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

	public class mouse2 implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			instructionBtn.setIcon(new ImageIcon(((new ImageIcon(
					this.getClass().getResource("/image/Button4.png"))).getImage()).getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			instructionBtn.setIcon(new ImageIcon(((new ImageIcon(
					this.getClass().getResource("/image/Button3.png"))).getImage()).getScaledInstance(200, 50,
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

	class btnClick1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public static void main (String args[]) throws IOException {
		MenuScreen w = new MenuScreen();
			
		}
	
	
	
}
