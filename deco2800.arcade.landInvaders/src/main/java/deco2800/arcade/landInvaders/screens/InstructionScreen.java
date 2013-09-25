package deco2800.arcade.landInvaders.screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class InstructionScreen {
	
	private JButton backBtn = new JButton();
	
	
	public static void main(String[] args) {
        new InstructionScreen();
    }

    public InstructionScreen() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new LoginPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class LoginPane extends JLabel {

        public LoginPane() {
        	setIcon(new ImageIcon(((new ImageIcon("/image/rule.png")).getImage()).getScaledInstance(800, 500, java.awt.Image.SCALE_SMOOTH)));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weighty = 1;
            gbc.weightx = 1;
            gbc.insets = new Insets(10, 2, 2, 2);
            gbc.anchor = GridBagConstraints.SOUTHEAST;
    		backBtn.setOpaque(false);
    		backBtn.setContentAreaFilled(false);
    		backBtn.setBorderPainted(false);
    		backBtn.setIcon(new ImageIcon(((new ImageIcon("/image/Button5.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
    		backBtn.addMouseListener(new mouse1());
    		backBtn.addActionListener(new btnClick1());
            add(backBtn, gbc);
        }
    }
    
    public class mouse1 implements MouseListener {
		
		public void mouseEntered(MouseEvent evt) {
			
			backBtn.setIcon(new ImageIcon(((new ImageIcon("/image/Button6.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
	    }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			backBtn.setIcon(new ImageIcon(((new ImageIcon("/image/Button5.png")).getImage()).getScaledInstance(200, 50, java.awt.Image.SCALE_SMOOTH)));
			
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
			new MenuScreen();
		}
	}
}

