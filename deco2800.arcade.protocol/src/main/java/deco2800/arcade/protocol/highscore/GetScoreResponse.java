package deco2800.arcade.protocol.highscore;


import deco2800.arcade.protocol.UserRequest;

/**
 * This protocol object is sent from server to client, containing the results
 * of a query specified by GetScoreRequest as a single string of comma 
 * separated values.
 * 
 * @author TeamA
 */
public class GetScoreResponse extends UserRequest {
	public String data;
}
