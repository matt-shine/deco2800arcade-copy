package deco2800.arcade.forum.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForumUIControl {
	public ForumUi view;
	//private ForumModel model;
	
	public ForumUIControl(ForumUi view) {
		this.view = view;
		//this.model = model;
		// 0 is Forum UI mode
		
	}
	
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			view.open_general_discussion();
		}
	}
	/*
	private ForumModel model;
	private ForumUi interf;
	
	public ForumControl(ForumModel model, ForumUi interf) {
		this.model = model;
		this.view = interf;
		this.view.addPostListener(new PostActionListener);
	}
	
	private class PostActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			this.view.postThread();
			this.view.updateThread();
		}
	}
	*/
}
