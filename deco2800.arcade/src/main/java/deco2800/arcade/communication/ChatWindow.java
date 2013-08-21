package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.List;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatWindow {
	
	List chatGroup;
	JTextArea textArea;
	JTextArea input;
	JButton sendButton;
	
	public ChatWindow(){
		//Create List of connected users.
		chatGroup = new List();
		
		//Create individual chat window.
		JFrame chat = new JFrame("Testing Chat");
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = chat.getContentPane();
		
		addTextArea(container);
		addInputArea(container);
		addSendButton(container);
		
		chat.pack();
		chat.setVisible(true);
		
	}
	
	private void addTextArea(Container container){
		textArea = new JTextArea(5,20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		container.add(scrollPane, BorderLayout.NORTH);
	}
	
	private void addInputArea(Container container){
		input = new JTextArea(5,20);
		container.add(input, BorderLayout.CENTER);
	}
	
	private void addSendButton(Container container){
		sendButton = new JButton("SEND");
		container.add(sendButton, BorderLayout.SOUTH);
	}
	
	//Add another user to the chat group.
	public void addUser(String username){
		chatGroup.add(username);
	}
	
	public void addSendButtonListener(ActionListener listener){
		sendButton.addActionListener(listener);
	}
	
	public void setTextInput(String text){
		input.setText(text);
	}
	
	public void setTextArea(String text){
		textArea.setText(text);
	}
	
	public String getTextInput(){
		return input.getText();
	}

}
