package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import deco2800.arcade.model.ChatNode;

public class CommunicationView extends JPanel {

	private JScrollPane scrollPane;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JButton sendButton;
	private JPanel scrollablePanel;
	private JPanel viewOne;
	private JPanel viewTwo;
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = this;
	private JButton backButton;

	public CommunicationView() {
		setPreferredSize(new Dimension(250, 600));
		setLayout(cardLayout);
		
		viewOne = new JPanel();
		viewOne.setBorder(null);
		viewOne.setPreferredSize(new Dimension(250, 800));
		
		viewTwo = new JPanel();
		viewTwo.setPreferredSize(new Dimension(250, 800));
		
		scrollablePanel = new JPanel();
		scrollablePanel.setPreferredSize(new Dimension(250, 800));
		
		outputArea = new JTextArea();
		outputArea.setPreferredSize(new Dimension(250, 375));
		outputArea.setEditable(false);
		
		inputArea = new JTextArea();
		inputArea.setPreferredSize(new Dimension(250, 250));
		
		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(250, 40));
		
		backButton = new JButton("< Back");
		backButton.setPreferredSize(new Dimension(250, 40));
		
		viewTwo.add(backButton, BorderLayout.NORTH);
		viewTwo.add(outputArea, BorderLayout.NORTH);
		viewTwo.add(inputArea, BorderLayout.NORTH);
		viewTwo.add(sendButton, BorderLayout.SOUTH);
		
		scrollPane = new JScrollPane(scrollablePanel);
		
		JLabel label = new JLabel("Michael, Smith");
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(250, 50));
		
		scrollablePanel.add(label, BorderLayout.PAGE_START);
		scrollablePanel.add(new JLabel("Ted, Ned"), BorderLayout.NORTH);
		viewOne.add(scrollPane, BorderLayout.PAGE_START);
		
		this.add(viewOne, "1");
		this.add(viewTwo, "2");
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
			}
		});
		
		
		label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				cardLayout.show(cardPanel, "2");
				JLabel label = (JLabel) e.getSource(); //Each label will correspond to a node. When its clicked we will get it and load the chat history.
				outputArea.setText("This is how we will load in the data...");
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}


	public void addChatNode(ChatNode node) {
		// scrollablePanel.add(node);
	}
	

	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}

	public String getMessage() { 
		return inputArea.getText();
	}
}
