package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class CommunicationView extends JFrame {

	private JScrollPane scrollPane;
	private JTextArea inputArea;
	private JButton sendButton;
	private Container container;
	private JLabel testLabel;
	private JPanel scrollablePanel;
	private JLabel testLabel2;

	public CommunicationView(){		
		setBounds(400,200,100,740);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		container = getContentPane();
		container.setBackground(Color.WHITE);
		
		scrollablePanel = new JPanel();
		scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(scrollablePanel);
		scrollPane.setPreferredSize(new Dimension(100, 400));

		inputArea = new JTextArea();
		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(100,100));
		
		container.add(scrollPane, BorderLayout.NORTH);
		container.add(inputArea, BorderLayout.CENTER);
		container.add(sendButton, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}
	
	public void addChatNode(ChatNode node){
		//scrollablePanel.add(node);
	}

	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}
	
	public String getMessage(){
		return inputArea.getText();
	}
	

}
