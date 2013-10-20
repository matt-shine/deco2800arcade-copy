package deco2800.arcade.communication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JLabel;

import deco2800.arcade.model.ChatMessage;
import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

public class CommunicationController {

	private CommunicationView view;
	private CommunicationNetwork network;
	private CommunicationModel model;
	private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
	private HashMap<Integer, JLabel> nodeLink = new HashMap<Integer, JLabel>();

	public CommunicationController(CommunicationView view,
			CommunicationModel model, CommunicationNetwork network) {
		this.view = view;
		this.model = model;
		this.network = network;

		view.addSendListener(new SendListener());
	}

	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			ChatNode node = network.getCurrentChat();

			// This should never be null, because the chat window won't be open
			// if it leads to nowhere... but it is during testing
			if (node != null) {
				TextMessage message = new TextMessage();
				message.setChatID(node.getID()); // Is this right?
				message.setSenderID(network.getPlayer().getID());
				message.setSenderUsername(network.getPlayer().getUsername());
				message.setText(view.getMessage());

				if (node.getParticipants() == null) {
					System.out
							.println("You are trying to send to nobody! This won't happen normally because a chat window will only be open if you have someone to talk to. "
									+ "It will however, happen during testing because this chat window is open by default!");
				} else {
					message.setRecipients(node.getParticipants());
					network.sendTextMessage(message);
					view.clearInput();
				}
			}
		}
	}
	
	
	public void addChatLabel(ChatNode node, String chatTitle) {
		
		Integer nodeID = node.getID();
		JLabel nodeLabel = new JLabel();
		nodeLink.put(nodeID, nodeLabel);
		nodeLabel.setName(nodeID.toString());
		
		int numParticipants = node.getParticipants().size();
		
		if (numParticipants == 2){
			nodeLabel.setText(chatTitle);
		} else if (numParticipants == 3){
			nodeLabel.setText(chatTitle + " and 1 other");
		} else {
			nodeLabel.setText(chatTitle + " and " + (numParticipants-2) + " others");
		}
		
		addMouseListener(nodeLabel);
		view.addLabel(nodeLabel);
		
	}
	
	
	
	public void addMouseListener(JLabel label){
		label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				view.changeView();
				
				JLabel label = (JLabel) e.getSource();
				label.setBackground(Color.WHITE);
				
				Integer chatID = Integer.parseInt(label.getName());
				
				ChatNode node = network.getChatNode(chatID);
				
				if (node != null){
					label.setBackground(Color.white);

					if (network.getCurrentChat() != node){
						network.setCurrentChat(node);
						for (String line : node.getChatHistory()){
							view.appendOutputArea(line);
						}
						
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				JLabel label = (JLabel) e.getSource();
				label.setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JLabel label = (JLabel) e.getSource();
				label.setBackground(Color.WHITE);
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


	public void receiveText(TextMessage message) {
		Date date = new Date();
		view.appendOutputArea(sdf.format(date) + " - " + message.getSenderUsername() + ": " + message.getText());
	}
	
	public void revieceNotification(ChatNode node) {
		Integer chatID = node.getID();
		JLabel label = nodeLink.get(chatID);
		view.displayNotification(label);
	}
	
	
}
