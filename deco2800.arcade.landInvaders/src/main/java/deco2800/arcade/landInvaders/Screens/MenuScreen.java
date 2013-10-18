package deco2800.arcade.landInvaders.Screens;

import java.awt.Color;
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
import deco2800.arcade.landInvaders.Screens.InstructionScreen.btnClick1;
import deco2800.arcade.landInvaders.Screens.InstructionScreen.mouse1;

public class MenuScreen extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JLabel intro = new JLabel();
	private JLabel HSIntro = new JLabel();
	private JLabel mainLabelContainer = new JLabel();
	private JButton instructionBtn = new JButton();
	private JButton newGameBtn = new JButton();
	private JButton highScoreBtn = new JButton();
	private JFrame s = this;
	private JButton backBtn = new JButton();
	private JButton HSbackBtn = new JButton();

	public MenuScreen(){
		super("LandInvaders");
		this.add(mainPanel);
		
		mainPanel.add(mainLabelContainer);
		//set background
		mainLabelContainer.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/main.png"))).getImage()).getScaledInstance(
						800, 500, java.awt.Image.SCALE_SMOOTH)));
		mainLabelContainer.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		// new game btn
		newGameBtn.setOpaque(false);
		newGameBtn.setContentAreaFilled(false);
		newGameBtn.setBorderPainted(false);
		newGameBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button1.png")))
				.getImage()).getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		newGameBtn.addMouseListener(new mouse1());
		mainLabelContainer.add(newGameBtn, gbc);
		//arrangment
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 2, 2, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		
		//instructions btn
		instructionBtn.setOpaque(false);
		instructionBtn.setContentAreaFilled(false);
		instructionBtn.setBorderPainted(false);
		instructionBtn.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/Button3.png"))).getImage())
				.getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		instructionBtn.addMouseListener(new mouse2());
		instructionBtn.addActionListener(new btnClick1());
		mainLabelContainer.add(instructionBtn, gbc);
		//arangement
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 2, 2, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		
		//highscore btn
		highScoreBtn.setOpaque(false);
		highScoreBtn.setContentAreaFilled(false);
		highScoreBtn.setBorderPainted(false);
		highScoreBtn.setIcon(new ImageIcon(
				((new ImageIcon(this.getClass().getResource("/image/highScoreBtnNoHover.png"))).getImage())
				.getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		highScoreBtn.addMouseListener(new HSmouse3());
		highScoreBtn.addActionListener(new btnClick1());
		mainLabelContainer.add(highScoreBtn, gbc);

		//instruction screen
		intro.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/rule.png")))
				.getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
		intro.setLayout(new GridBagLayout());
		GridBagConstraints gbd = new GridBagConstraints();
		gbd.gridx = 2; 
		gbd.gridy = 2;
		gbd.gridwidth = 1; 
		gbd.gridheight = 1;
		gbd.weighty = 0;
		gbd.weightx = 1;
		gbd.insets = new Insets(300, 2, 2, 2);
		gbd.anchor = GridBagConstraints.SOUTHEAST;
		
		//high score screen
		HSIntro.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/HScore.png")))
				.getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
		HSIntro.setLayout(new GridBagLayout());
		GridBagConstraints gbdHS = new GridBagConstraints();
		gbdHS.gridx = 2; 
		gbdHS.gridy = 2;
		gbdHS.gridwidth = 1; 
		gbdHS.gridheight = 1;
		gbdHS.weighty = 0;
		gbdHS.weightx = 1;
		gbdHS.insets = new Insets(300, 2, 2, 2);
		gbdHS.anchor = GridBagConstraints.SOUTHEAST;
		
		// back btn
		backBtn.setOpaque(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button5.png")))
				.getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		backBtn.addMouseListener(new back());
		intro.add(backBtn, gbd);

		this.setVisible(true);
		this.setSize(800,530);
		
		// HS back btn
		HSbackBtn.setOpaque(false);
		HSbackBtn.setContentAreaFilled(false);
		HSbackBtn.setBorderPainted(false);
		HSbackBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button5.png")))
				.getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		HSbackBtn.addMouseListener(new HSback());
		HSIntro.add(HSbackBtn, gbdHS);

		this.setVisible(true);
		this.setSize(800,530);

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
			Invaders m = new Invaders();
			s.dispose();

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

			mainPanel.removeAll();
			mainPanel.add(intro);
			mainPanel.revalidate();
			mainPanel.repaint();
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


	public class back implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			backBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button6.png")))
					.getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		}
			
		@Override
		public void mouseClicked(MouseEvent e) {
			mainPanel.removeAll();
			mainPanel.add(mainLabelContainer);
			mainPanel.revalidate();
			mainPanel.repaint();

		}

		@Override
		public void mouseExited(MouseEvent e) {
			backBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button5.png"))).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));

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

	public class HSback implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			HSbackBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button6.png")))
					.getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		}
			
		@Override
		public void mouseClicked(MouseEvent e) {
			mainPanel.removeAll();
			mainPanel.add(mainLabelContainer);
			mainPanel.revalidate();
			mainPanel.repaint();

		}

		@Override
		public void mouseExited(MouseEvent e) {
			HSbackBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button5.png"))).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));

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
	
	public static void main (String args[]) throws IOException {
		MenuScreen w = new MenuScreen();
	}
	
	public class HSmouse3 implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			highScoreBtn.setIcon(new ImageIcon(((new ImageIcon(
					this.getClass().getResource("/image/highScoreBtnOnHover.png"))).getImage()).getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			mainPanel.removeAll();
			mainPanel.add(HSIntro);
			mainPanel.revalidate();
			mainPanel.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			highScoreBtn.setIcon(new ImageIcon(((new ImageIcon(
					this.getClass().getResource("/image/highScoreBtnNoHover.png"))).getImage()).getScaledInstance(200, 50,
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
