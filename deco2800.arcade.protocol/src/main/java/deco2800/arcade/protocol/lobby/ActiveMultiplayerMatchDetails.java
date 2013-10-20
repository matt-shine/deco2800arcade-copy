package deco2800.arcade.protocol.lobby;

public class ActiveMultiplayerMatchDetails extends LobbyRequest {

	public int hostPlayerId;
	public String hostName;
	// public Connection hostConnection; NOT SURE IF NECESSARY YET.
	public int opponentPlayerId;
	public String opponentName;
	public String gameId;
	public int matchId;

}
