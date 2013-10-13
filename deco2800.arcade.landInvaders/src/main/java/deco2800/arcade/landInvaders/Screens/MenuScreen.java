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

public class MenuScreen extends JLabel{

	private JButton instructionBtn = new JButton();
	private JButton newGameBtn = new JButton();

	public MenuScreen(){
		setIcon(new ImageIcon(
				((new ImageIcon("Main.png")).getImage()).getScaledInstance(
						800, 500, java.awt.Image.SCALE_SMOOTH)));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.gridx = 0;
		gbc.gridy = 0;

		newGameBtn.setOpaque(false);
		newGameBtn.setContentAreaFilled(false);
		newGameBtn.setBorderPainted(false);
		newGameBtn.setIcon(new ImageIcon(((new ImageIcon("Button1.png"))
				.getImage()).getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		newGameBtn.addMouseListener(new mouse1());
		add(newGameBtn, gbc);
		gbc.gridy++;

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 2, 2, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		instructionBtn.setOpaque(false);
		instructionBtn.setContentAreaFilled(false);
		instructionBtn.setBorderPainted(false);
		instructionBtn.setIcon(new ImageIcon(
				((new ImageIcon("Button3.png")).getImage())
				.getScaledInstance(200, 50,
						java.awt.Image.SCALE_SMOOTH)));
		instructionBtn.addMouseListener(new mouse2());
		instructionBtn.addActionListener(new btnClick1());
		add(instructionBtn, gbc);

	}

	public class mouse1 implements MouseListener {

		public void mouseEntered(MouseEvent evt) {

			newGameBtn.setIcon(new ImageIcon(
					((new ImageIcon("Button2.png")).getImage())
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
					((new ImageIcon("Button1.png")).getImage())
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
					"Button4.png")).getImage()).getScaledInstance(200, 50,
							java.awt.Image.SCALE_SMOOTH)));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			instructionBtn.setIcon(new ImageIcon(((new ImageIcon(
					"Button3.png")).getImage()).getScaledInstance(200, 50,
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
}
