package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

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
	private Date date;
	private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
	
	private HashMap<Integer, ChatNode> chatNodes = new HashMap<Integer, ChatNode>();
	private CommunicationNetwork communicationNetwork;

	public CommunicationView() {

		setLayout(cardLayout);
		
		createViewOne();
		createViewTwo();

		add(viewOne, "1");
		add(viewTwo, "2");
		
		
//		label.addMouseListener(new MouseListener() {
//			public void mouseClicked(MouseEvent e) {
//				cardLayout.show(cardPanel, "2");
//				JLabel chatNode = (JLabel) e.getSource();
//			}
//
//			public void mouseEntered(MouseEvent e) {
//				JLabel chatNode = (JLabel) e.getSource();
//				chatNode.setBackground(Color.GRAY);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				JLabel chatNode = (JLabel) e.getSource();
//				chatNode.setBackground(Color.WHITE);
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});

	}
	
	private void createViewOne() {
		
		scrollablePanel = new JPanel();
		scrollablePanel.setPreferredSize(new Dimension(250, 800));
		
		viewOne = new JPanel(new GridLayout(1,1,0,0));
		scrollPane = new JScrollPane(scrollablePanel);
		
		JLabel l = new JLabel("Michael, Smith");
		l.setBackground(Color.WHITE);
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setPreferredSize(new Dimension(250, 50));
		l.setName("test1");
		addMouseListener(l);
		
		scrollablePanel.add(l);
		
		viewOne.add(scrollPane);
		
	}
	
	private void createViewTwo() {
		
		viewTwo = new JPanel();
		viewTwo.setPreferredSize(new Dimension(250, 800));
		
		outputArea = new JTextArea();
		outputArea.setPreferredSize(new Dimension(250, 375));
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		
		inputArea = new JTextArea();
		inputArea.setPreferredSize(new Dimension(250, 250));
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		
		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(250, 40));
		
		backButton = new JButton("< Back");
		backButton.setPreferredSize(new Dimension(250, 40));
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
			}
		});

		viewTwo.add(backButton, BorderLayout.NORTH);
		viewTwo.add(outputArea, BorderLayout.NORTH);
		viewTwo.add(inputArea, BorderLayout.NORTH);
		viewTwo.add(sendButton, BorderLayout.SOUTH);
		
	}

	public void addChatNode(ChatNode node) {
		Integer nodeID = node.getID();
		chatNodes.put(nodeID, node);
		
		//This will be the sender(s)'s username eventually. For now, just get the playerID of the first participant
		String labelID = node.getParticipants().get(0).toString();
		
		JLabel nodeLabel = new JLabel(labelID);
		nodeLabel.setName(nodeID.toString());
		nodeLabel.setBackground(Color.WHITE);
		nodeLabel.setOpaque(true);
		nodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nodeLabel.setPreferredSize(new Dimension(250, 50));
		
		scrollablePanel.add(nodeLabel, BorderLayout.NORTH);
		addMouseListener(nodeLabel);
		viewOne.revalidate();
		viewOne.repaint();
	}
	

	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}
	
	public void addMouseListener(JLabel label){
		label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel, "2");
				JLabel label = (JLabel) e.getSource();
				ChatNode chatNode = null;
				try{
					chatNode = chatNodes.get(Integer.parseInt(label.getName()));
				} catch (NumberFormatException nfe) {
					//String did not contain a number
				}
				
				if (chatNode != null){
					if (communicationNetwork.getCurrentChat() != chatNode){
						communicationNetwork.setCurrentChat(chatNode);
						for (String line : chatNode.getChatHistory()){
							outputArea.append("\n" + line);
						}
					}
				}
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

	public String getMessage() { 
		return inputArea.getText();
	}
	
	public void receiveText(TextMessage message){
		date = new Date();
		outputArea.append("\n" + sdf.format(date) + " - " + message.getSenderUsername() + ": " + message.getText());
	}
	
	public void clearInput(){
		inputArea.setText("");
	}


	public void setCommunicationNetwork(CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
	}
}
