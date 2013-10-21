package deco2800.arcade.communication;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JLabel;

import deco2800.arcade.model.ChatNode;
import deco2800.arcade.protocol.communication.TextMessage;

/**
 * Controls what occurs in the view. When messages are received or sent
 * through the communicationNetwork they are passed through the controller
 * and displayed on the view. The controller also handles receiving messages
 * from the view and passing them to the communicationNetwork.
 * 
 */
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
		view.addKeyPressListener(new KeyPressedListener());
	}
	/**
	 * Listener for the send button
	 */
	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			send();
		}
	}
	/**
	 * Listener for keyboard actions
	 */
	private class KeyPressedListener implements KeyListener {
		
		/**
		 * Sends message if enter key is pressed
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				send();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}


	/**
	 * Creates a TextMessage from the text in the CommunicationView and sends it
	 * to the participants of the current chat.
	 */
	private void send() {
		ChatNode node = network.getCurrentChat();
		String message = view.getMessage();
		
		if(message.startsWith("/?") || message.startsWith("/help")){
			view.appendOutputArea("To add a user to the conversation, type /invite playerID\n");
			view.appendOutputArea("To remove a user from the conversation, type /kick playerID\n");
		} else if(message.startsWith("/invite")) {
			String playerID = message.substring(8);
			node.addParticipant(Integer.parseInt(playerID));
			message = "Player " + playerID + " added.";
			view.appendOutputArea(message);
		} else if(message.startsWith("/kick")) {
			String playerID = message.substring(6);
			node.removeParticipant(Integer.parseInt(playerID));
			message = "Player " + playerID + " removed.";
			view.appendOutputArea(message);
		}

		// This should never be null, because the chat window won't be open
		// if it leads to nowhere... but it is during testing
		if (node != null) {
			TextMessage textMessage = new TextMessage();
			textMessage.setChatID(node.getID()); // Is this right?
			textMessage.setSenderID(network.getPlayer().getID());
			textMessage.setSenderUsername(network.getPlayer().getUsername());
			textMessage.setText(message);

			if (node.getParticipants() == null) {
			} else {
				textMessage.setRecipients(node.getParticipants());
				network.sendTextMessage(textMessage);
				view.clearInput();
			}
		}
	}

	/**
	 * Label for the chat sidebar to display current chats opened. Can be
	 * clicked on to open this chat.
	 * 
	 * @param node
	 *            The ChatNode for the chat being represented by this ChatLabel
	 * @param chatTitle
	 *            The text to be displayed on this label to inform user of who
	 *            is in the chat
	 */
	public void addChatLabel(ChatNode node, String chatTitle) {
		Integer nodeID = node.getID();
		JLabel nodeLabel = new JLabel();
		nodeLink.put(nodeID, nodeLabel);
		nodeLabel.setName(nodeID.toString());

		int numParticipants = node.getParticipants().size();

		if (numParticipants == 2) {
			nodeLabel.setText(chatTitle);
		} else if (numParticipants == 3) {
			nodeLabel.setText(chatTitle + " and 1 other");
		} else {
			nodeLabel.setText(chatTitle + " and " + (numParticipants - 2)
					+ " others");
		}

		addMouseListener(nodeLabel);
		view.addLabel(nodeLabel);
	}

	/**
	 * MouseListener for the ChatLabels, changes view when label is clicked so
	 * that the input and output area can be seen and messages can be
	 * sent/recieved.
	 * 
	 * @param label
	 *            The ChatLabel that the MouseListener will be added to
	 */
	public void addMouseListener(JLabel label) {
		label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

				view.changeView();

				JLabel label = (JLabel) e.getSource();
				label.setBackground(Color.WHITE);

				Integer chatID = Integer.parseInt(label.getName());

				ChatNode node = network.getChatNode(chatID);

				if (node != null) {
					label.setBackground(Color.white);

					if (network.getCurrentChat() != node) {
						network.setCurrentChat(node);
						for (String line : node.getChatHistory()) {
							view.appendOutputArea(line);
						}

					}
				}
			}

			/**
			 * Changes the colour of the label when the Mouse hovers over it
			 */
			public void mouseEntered(MouseEvent e) {
				JLabel label = (JLabel) e.getSource();
				label.setBackground(Color.GRAY);
			}

			/**
			 * Changes colour of label when mouse leaves
			 */
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

	/**
	 * Converts the text in the input area to a Time - Username: message format
	 * for the output area of the recipients and for chatHistory
	 * 
	 * @param message
	 *            the text message in the input area
	 */
	public void receiveText(TextMessage message) {
		Date date = new Date();
		view.appendOutputArea(sdf.format(date) + " - "
				+ message.getSenderUsername() + ": " + message.getText());
	}

	/**
	 * If a notification is recieved the label corresponding to the ChatNode
	 * will turn red, indicating an update to the player
	 * 
	 * @param node
	 *            The ChatNode that recieved the update
	 */
	public void revieceNotification(ChatNode node) {
		Integer chatID = node.getID();
		JLabel label = nodeLink.get(chatID);
		view.displayNotification(label);
	}

}
