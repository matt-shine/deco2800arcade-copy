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

public class InstructionScreen extends JLabel{
	
	private JButton backBtn = new JButton();
    
	public InstructionScreen() {
    	setIcon(new ImageIcon(((new ImageIcon("rule.png")).getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 2, 2, 2);
        gbc.anchor = GridBagConstraints.SOUTHEAST;
		backBtn.setOpaque(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setIcon(new ImageIcon(((new ImageIcon("Button5.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
		backBtn.addMouseListener(new mouse1());
		backBtn.addActionListener(new btnClick1());
        add(backBtn, gbc);
    }
	
public class mouse1 implements MouseListener {
		
		public void mouseEntered(MouseEvent evt) {
			
			backBtn.setIcon(new ImageIcon(((new ImageIcon("Button6.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
	    }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			backBtn.setIcon(new ImageIcon(((new ImageIcon("Button5.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
			
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
			//
		}
	}

}
