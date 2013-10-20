package deco2800.arcade.userui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.userui.Model;
import deco2800.arcade.userui.view.BlockScreen;
import deco2800.arcade.userui.view.RemoveFriendScreen;
import deco2800.arcade.userui.view.UserScreen;

public class BlockFriend {
	
	private BlockScreen blockView;
	private UserScreen userView;
	private Model theModel;

	/**
	 * Controller for the Block Friend View
	 * @param theModel
	 * @param friendView
	 */
	public BlockFriend(Model theModel, UserScreen userView, BlockScreen blockView){
		
		this.theModel = theModel;
		this.blockView = blockView;
		this.userView = userView;

		this.blockView.addBlockListener(new BlockListener());
		this.blockView.addUnblockListener(new UnblockListener());
		
	}

	
	class BlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			blockView.updateblocklist(blockView.getinput());
			userView.setEnabled(true);
			
		}
		
	}
	

	class UnblockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			blockView.removeblocklist();
			blockView.dispose();
			userView.setEnabled(true);
			
		}
		
	}
	

	

}
