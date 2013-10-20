package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


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
			}
		});

		viewTwo.add(backButton, BorderLayout.NORTH);
		viewTwo.add(outputArea, BorderLayout.NORTH);
		viewTwo.add(inputArea, BorderLayout.NORTH);
		viewTwo.add(sendButton, BorderLayout.SOUTH);

	}

	public void addLabel(JLabel label) {

		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(250, 50));
		
		scrollablePanel.add(label, BorderLayout.NORTH);
		viewOne.revalidate();
		viewOne.repaint();
	}
	
	
	public void changeView() {
		cardLayout.next(cardPanel);
	}
	
	public void appendOutputArea(String line) {
		outputArea.append("\n" + line);
	}
	

	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}


	public String getMessage() {
		return inputArea.getText();
	}
	
	public void clearInput(){
		inputArea.setText("");
	}
	
	public void displayNotification(JLabel label){
		label.setBackground(Color.RED);
		viewOne.revalidate();
		viewOne.repaint();
	}

	public void setCommunicationNetwork(
			CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
	}
}
