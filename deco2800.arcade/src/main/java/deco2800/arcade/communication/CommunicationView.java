package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Handles the appearance of the chat side bar. Creates a card Layout that
 * lets the user switch between a list of current chats and being able to
 * participant in a chat.
 */
public class CommunicationView extends JPanel {

	private static final long serialVersionUID = -2843626122426089843L;
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

	/**
	 * Creates a new side bar
	 * 
	 * @param height
	 *            the height of the side bar
	 */
	public CommunicationView(int height) {

		setPreferredSize(new Dimension(250, height));
		setLayout(cardLayout);
		createViewOne();
		createViewTwo();
		add(viewOne, "1");
		add(viewTwo, "2");
	}

	/**
	 * Creates the first view of the sidebar. This is where all of the
	 * friends/chats will be displayed for the player to click on and initiate a
	 * chat.
	 */
	private void createViewOne() {

		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		viewOne = new JPanel(new GridLayout(1, 1, 0, 0));
		viewOne.setBorder(border);

		scrollablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1)) {
			private static final long serialVersionUID = 1L;

			/* Overrides the paintComponent to give the panel a gradient background.
			 * Due to swings lack of visual theme we are trying to make the chat bar
			 * blend in with the arcade.
			 */
			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA
						.brighter().brighter(), 0, getHeight(), Color.BLACK
						.darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}

		};

		scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
		scrollablePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		
		scrollPane = new JScrollPane(scrollablePanel,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);

		viewOne.add(scrollPane, BorderLayout.PAGE_START);
	}

	/**
	 * Creates the second view of the sidebar. This is where the input and
	 * output textAreas are located and where the player can type/send and
	 * receive messages.
	 */
	private void createViewTwo() {

		viewTwo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1)) {
			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA
						.brighter().brighter(), 0, getHeight(), Color.BLACK
						.darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		outputArea = new JTextArea();
		outputArea.setBackground(Color.MAGENTA);
		outputArea.setPreferredSize(new Dimension(250, 350));
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		outputArea.setOpaque(false);
		outputArea.setBorder(new CompoundBorder(
				new EmptyBorder(10, 10, 10, 10), new LineBorder(
						Color.LIGHT_GRAY)));

		inputArea = new JTextArea();
		inputArea.setBackground(Color.MAGENTA);
		inputArea.setPreferredSize(new Dimension(250, 300));
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		inputArea.setOpaque(false);
		inputArea.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
				new LineBorder(Color.LIGHT_GRAY)));

		sendButton = new JButton("Send");
		sendButton.setContentAreaFilled(false);
		sendButton.setBorderPainted(false);
		sendButton.setOpaque(false);
		sendButton.setPreferredSize(new Dimension(250, 30));

		backButton = new JButton("< Back");
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setOpaque(false);
		backButton.setPreferredSize(new Dimension(250, 30));

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

	/**
	 * Adds a label to ViewOne to represent a chat/friend.
	 * 
	 * @param label
	 *            the label to be used as a display
	 */
	public void addLabel(JLabel label) {
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		label.setMaximumSize(new Dimension(250, 50));
		label.setBorder(border);

		scrollablePanel.add(label, BorderLayout.NORTH);
		scrollablePanel.add(Box.createVerticalStrut(1));
		viewOne.revalidate();
		viewOne.repaint();
		scrollablePanel.revalidate();
		scrollablePanel.repaint();
	}

	/**
	 * If in ViewOne change to ViewTwo. If in ViewTwo change to ViewOne.
	 */
	public void changeView() {
		cardLayout.next(cardPanel);
	}

	/**
	 * Adds text to the output area.
	 * 
	 * @param line
	 *            text to be added
	 */
	public void appendOutputArea(String line) {
		outputArea.append("\n" + line);
	}

	/**
	 * Adds listener to the send button in ViewTwo
	 * 
	 * @param listener
	 *            ActionListener to be used
	 */
	public void addSendListener(ActionListener listener) {
		sendButton.addActionListener(listener);
	}

	/**
	 * Gets the text from the inputArea
	 * 
	 * @return String
	 */
	public String getMessage() {
		return inputArea.getText();
	}

	/**
	 * Clears the inputArea
	 */
	public void clearInput() {
		inputArea.setText("");
	}

	/**
	 * Changes colour of label to red
	 * 
	 * @param label
	 *            to be changed
	 */
	public void displayNotification(JLabel label) {
		label.setBackground(Color.RED);
		viewOne.revalidate();
		viewOne.repaint();
	}

	/**
	 * Sets the communicationNetwork that this view is using.
	 * 
	 * @param communicationNetwork
	 *            to be used
	 */
	public void setCommunicationNetwork(
			CommunicationNetwork communicationNetwork) {
		this.communicationNetwork = communicationNetwork;
	}

	/**
	 * Adds keyboardlistener to the view.
	 * 
	 * @param listener
	 *            to be used
	 */
	public void addKeyPressListener(KeyListener listener) {
		sendButton.addKeyListener(listener);
	}
}
