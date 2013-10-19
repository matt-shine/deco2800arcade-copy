package deco2800.arcade.landInvaders.Screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HighScoreScreen extends JLabel {
	private JButton backBtn = new JButton();

	public HighScoreScreen() {
		//Background
		setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/HScore.png"))).getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 2; 
		gbc.gridy = 2;
		gbc.gridwidth = 1; 
		gbc.gridheight = 1;
		gbc.weighty = 0;
		gbc.weightx = 1;
		gbc.insets = new Insets(300, 2, 2, 2);
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		backBtn.setOpaque(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		//BackButton
		backBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button5.png"))).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		backBtn.addMouseListener(new mouse1());
		backBtn.addActionListener(new btnClick1());
		add(backBtn, gbc);
	}

	public class mouse1 implements MouseListener {
		public void mouseEntered(MouseEvent evt) {

			backBtn.setIcon(new ImageIcon(((new ImageIcon(this.getClass().getResource("/image/Button6.png"))).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

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

	static class btnClick1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
