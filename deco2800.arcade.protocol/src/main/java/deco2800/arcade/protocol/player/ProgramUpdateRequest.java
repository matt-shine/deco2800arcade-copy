package deco2800.arcade.protocol.player;

import deco2800.arcade.protocol.NetworkObject;

public class ProgramUpdateRequest extends NetworkObject {
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
