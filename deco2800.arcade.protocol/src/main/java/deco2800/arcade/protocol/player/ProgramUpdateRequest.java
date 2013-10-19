package deco2800.arcade.protocol.player;

public class ProgramUpdateRequest extends PlayerNetworkObject {
	private int playerID;
	private String program;
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
}
