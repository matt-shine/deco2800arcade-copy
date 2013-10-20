package deco2800.arcade.protocol.player;

/**
 * Library Style update request
 * @author Aaron Hayes
 */
public class LibraryStyleUpdateRequest extends PlayerNetworkObject {
    private int playerID;
    private int colour;
    private int style;

    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    public int getColour() {
        return colour;
    }
    public void setColour(int colour) {
        this.colour = colour;
    }
    public int getStyle() {
        return style;
    }
    public void setStyle(int style) {
        this.style = style;
    }

}
