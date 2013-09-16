package deco2800.arcade.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.communication.CommunicationView;

public class CommunicationController {

	private CommunicationView view;
	private CommunicationNetwork network;
	private CommunicationModel model;

	public CommunicationController(CommunicationView view, CommunicationModel model, CommunicationNetwork network){
		this.view = view;
		this.model = model;
		this.network = network;
		
		view.addSendListener(new SendListener());
	}
	
	private class SendListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String message = view.getMessage();
			ChatNode node = network.getCurrentChat();
			network.sendTextMessage(node, message);
		}
	}
	
	
	
}
