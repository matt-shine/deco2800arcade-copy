package deco2800.arcade.communication;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class CommunicationView {
	
	JTextArea textArea;
	JTextArea input;
	JButton sendButton;
	JFrame chat = new JFrame("Chat Window");
	
	public CommunicationView(){	
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = chat.getContentPane();
		
		addTextArea(container);
		addInputArea(container);
		addSendButton(container);
		
		chat.pack();
		chat.setVisible(true);
	}
	
	private void addTextArea(Container container){
		textArea = new JTextArea(8,20);
		textArea.setEditable(false);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); //This makes the scroll bar always at the bottom
		
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
	
	public void addSendButtonListener(ActionListener listener){
		sendButton.addActionListener(listener);
	}
	
	public void setTextInput(String text){
		input.setText(text);
	}
	
	public void appendTextArea(String text){
		//
		textArea.append(text);
	}
	
	public String getTextInput(){
		return input.getText();
	}
	
	public void setWindowSize(int width, int height){
		chat.setSize(width, height);
	}
	
	public void setWindowTitle(String title){
		chat.setTitle(title);
	}

}
