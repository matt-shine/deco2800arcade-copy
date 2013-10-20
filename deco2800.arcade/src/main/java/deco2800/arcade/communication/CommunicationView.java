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

import deco2800.arcade.model.ChatMessage;
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
	private CardLayout cardLayout = new CardLayout(0, 0);
	private JPanel cardPanel = this;
	private JButton backButton;
	private Date date;
	private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

	private HashMap<Integer, ChatNode> chatNodes = new HashMap<Integer, ChatNode>();
	private HashMap<Integer, JLabel> chatNodeLabels = new HashMap<Integer, JLabel>();
	private CommunicationNetwork communicationNetwork;

	public CommunicationView() {
		setPreferredSize(new Dimension(250, 600));

		setLayout(cardLayout);
		createViewOne();
		createViewTwo();
		add(viewOne, "1");
		add(viewTwo, "2");
	}

	private void createViewOne() {

		viewOne = new JPanel(new GridLayout(1, 1, 0, 0));

		scrollablePanel = new JPanel();
		scrollPane = new JScrollPane(scrollablePanel,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel l = new JLabel("Michael, Smith");
		l.setBackground(Color.WHITE);
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setPreferredSize(new Dimension(250, 50));
		l.setName("test1");
		addMouseListener(l);

		scrollablePanel.add(l, BorderLayout.PAGE_START);
		viewOne.add(scrollPane, BorderLayout.PAGE_START);

	}

	private void createViewTwo() {

		viewTwo = new JPanel();

		outputArea = new JTextArea();
		outputArea.setPreferredSize(new Dimension(250, 375));
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);

		inputArea = new JTextArea();
		inputArea.setPreferredSize(new Dimension(250, 266));
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);

		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(250, 35));

		backButton = new JButton("< Back");
		backButton.setPreferredSize(new Dimension(250, 25));

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "1");
				communicationNetwork.setCurrentChat(null);
				outputArea.setText("");
			}
		});

		viewTwo.add(backButton, BorderLayout.NORTH);
		viewTwo.add(outputArea, BorderLayout.NORTH);
		viewTwo.add(inputArea, BorderLayout.NORTH);
		viewTwo.add(sendButton, BorderLayout.SOUTH);

	}

	public void addChatNode(ChatNode node, String senderUsername) {
		Integer nodeID = node.getID();
		chatNodes.put(nodeID, node);
		JLabel nodeLabel = null;

		int numParticipants = node.getParticipants().size();
		if (numParticipants == 2) {
			nodeLabel = new JLabel(senderUsername);
		} else if (numParticipants == 3) {
			nodeLabel = new JLabel(senderUsername + " and 1 other");
		} else {
			nodeLabel = new JLabel(senderUsername + " and "
					+ (numParticipants - 2) + " others");
		}

		nodeLabel.setName(nodeID.toString());
		nodeLabel.setBackground(Color.WHITE);
		nodeLabel.setOpaque(true);
		nodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nodeLabel.setPreferredSize(new Dimension(250, 50));

		chatNodeLabels.put(nodeID, nodeLabel);
		scrollablePanel.add(nodeLabel, BorderLayout.NORTH);
		addMouseListener(nodeLabel);
		viewOne.revalidate();
		viewOne.repaint();
	}

	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}

	public void addMouseListener(JLabel label) {
		label.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel, "2");
				JLabel label = (JLabel) e.getSource();
				ChatNode chatNode = null;
				try {
					chatNode = chatNodes.get(Integer.parseInt(label.getName()));
				} catch (NumberFormatException nfe) {
					// String did not contain a number
				}

				if (chatNode != null) {
					label.setBackground(Color.white);
					chatNodeLabels.put(chatNode.getID(), label);
					if (communicationNetwork.getCurrentChat() != chatNode) {
						communicationNetwork.setCurrentChat(chatNode);
						for (ChatMessage<String, String> line : chatNode
								.getChatHistory()) {
							outputArea.append("\n" + line.getMessage());
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

	public String getMessage() {
		return inputArea.getText();
	}

	public void receiveText(TextMessage message) {
		date = new Date();
		outputArea.append("\n" + sdf.format(date) + " - "
				+ message.getSenderUsername() + ": " + message.getText());
	}

	public void clearInput() {
		inputArea.setText("");
	}

	public void chatNotification(ChatNode node) {
		int nodeID = node.getID();
		JLabel nodeLabel = chatNodeLabels.get(nodeID);
		nodeLabel.setBackground(Color.RED);
		chatNodeLabels.put(nodeID, nodeLabel);
		viewOne.revalidate();
		viewOne.repaint();
	}

	public void setCommunicationNetwork(
			CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
	}
}
