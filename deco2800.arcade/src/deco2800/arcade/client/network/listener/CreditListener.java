package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;

public class CreditListener extends NetworkListener {

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof CreditBalanceResponse){
			CreditBalanceResponse creditBalanceResponse = (CreditBalanceResponse) object;
			Arcade arcade = Arcade.getInstance();
			arcade.setTitle("Credit Balance: " + Integer.toString(creditBalanceResponse.balance));
		}
	}

	
}
