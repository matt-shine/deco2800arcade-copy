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

import deco2800.arcade.model.ChatNode;

public class CommunicationView extends JPanel {

	private JScrollPane scrollPane;
	private JTextArea inputArea;
	private JButton sendButton;
	private JPanel scrollablePanel;

	public CommunicationView() {
		
		setPreferredSize(new Dimension(250, 500));

		scrollablePanel = new JPanel();
		scrollablePanel.setLayout(new BoxLayout(scrollablePanel,
				BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(scrollablePanel);
		scrollPane.setPreferredSize(new Dimension(250, 400));

		inputArea = new JTextArea();
		sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(250, 100));

		this.add(scrollPane, BorderLayout.NORTH);
		this.add(inputArea, BorderLayout.CENTER);
		this.add(sendButton, BorderLayout.SOUTH);

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
