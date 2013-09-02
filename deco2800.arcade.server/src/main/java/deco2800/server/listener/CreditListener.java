package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

public class CreditListener extends Listener {

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof CreditBalanceRequest){
			CreditBalanceRequest creditBalanceRequest = (CreditBalanceRequest) object;
			int playerID = creditBalanceRequest.playerID;
			try {
				Integer result = ArcadeServer.instance().getCreditStorage().getUserCredits(playerID);

				CreditBalanceResponse creditBalanceResponse = new CreditBalanceResponse();

				if (result == null){
					creditBalanceResponse.balance = -1;
					creditBalanceResponse.description = "No credit balance found";
				} else {
					creditBalanceResponse.balance = result;
					creditBalanceResponse.description = "OK";
				}
				
				connection.sendTCP(creditBalanceResponse);
				
			} catch (DatabaseException e) {
				e.printStackTrace();
				CreditBalanceResponse creditBalanceResponse = new CreditBalanceResponse();
				creditBalanceResponse.balance = -1;
				creditBalanceResponse.description = e.getMessage();
				connection.sendTCP(creditBalanceResponse);
			}
		}
	}


}
