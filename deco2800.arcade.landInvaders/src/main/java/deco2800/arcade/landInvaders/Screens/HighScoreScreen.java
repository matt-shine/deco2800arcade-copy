package deco2800.arcade.landInvaders.Screens;

import java.awt.Color;
import java.awt.Font;
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

import com.sun.tools.javac.util.List;

import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;

public class HighScoreScreen extends JLabel {
	private JButton backBtn = new JButton();
	private List<Highscore>  l;

	public HighScoreScreen(HighscoreClient player) {
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
		l = (List<Highscore>) player.getGameTopPlayers(10, true, "score");
		String dis="";
		for(int i =0; i<l.size(); i ++){
			dis  = dis + l.get(i).playerName +"-------" + l.get(i).score+ "\n"; 
		}
		
        JLabel e = new JLabel();
		
		GridBagConstraints gbd = new GridBagConstraints();
		gbd.anchor = GridBagConstraints.CENTER;
		gbd.insets = new Insets(0,150,60,0);
		gbd.gridx = 0;
		gbd.gridy = 0;
		this.add(e,gbd);
		e.setForeground(Color.black);
		e.setFont(new Font("Algerian",1, 40));
		e.setText(dis);

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
