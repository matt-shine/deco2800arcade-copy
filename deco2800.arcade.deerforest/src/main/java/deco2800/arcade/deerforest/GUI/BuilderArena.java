package deco2800.arcade.deerforest.GUI;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class BuilderArena extends Sprite{
	 
	//time time to move / scale sprites
    final private int timeToMove = 20;
    
    //Zone widths / heights (ratio to window size)
  	final private float deckZoneWidth = 0.045f;
  	final private float deckZoneHeight = 0.1f;
  	
  	final private float cardZoneWidth = 0.048f;
  	final private float cardZoneHeight = 0.12f;
  	
  	final private float zoomZoneHeight = 0.4f;
  	final private float zoomZoneWidth = 0.15f;
  	
  	//40 Deck zones
  	final private float deckZoneY1 = 0.38f; //constant for all P1 monster zones
	final private float deckZoneY2 = 0.27f; //constant for all P2 monster zones
	final private float deckZoneY3 = 0.16f;
	final private float deckZoneY4 = 0.05f;
	
	final private float deckZoneX1 = 0.1f;
	final private float deckZoneX2 = 0.17f;
	final private float deckZoneX3 = 0.24f;
	final private float deckZoneX4 = 0.31f;
	final private float deckZoneX5 = 0.38f;
	final private float deckZoneX6 = 0.45f;
	final private float deckZoneX7 = 0.52f;
	final private float deckZoneX8 = 0.59f;
	final private float deckZoneX9 = 0.66f;
	final private float deckZoneX10 = 0.73f;
	
	final private float[] deckZonesY = {deckZoneY1, deckZoneY2, deckZoneY3,
			deckZoneY4};
	final private float[] deckZonesX = {deckZoneX1, deckZoneX2, deckZoneX3,
			deckZoneX4, deckZoneX5, deckZoneX6, deckZoneX7, deckZoneX8, 
			deckZoneX9, deckZoneX10};
	
	
	
	//5 Card Zones 
	final private float cardZoneY = 0.60f;//constant for the card zones
	
	final private float cardZoneX1 = 0.245f;
	final private float cardZoneX2 = 0.307f;
	final private float cardZoneX3 = 0.369f;
	final private float cardZoneX4 = 0.431f;
	final private float cardZoneX5 = 0.493f;
	final private float cardZoneX6 = 0.555f;
	final private float cardZoneX7 = 0.617f;
	
	final private float[] cardZonesX = {cardZoneX1, cardZoneX2, cardZoneX3, cardZoneX4, cardZoneX5, 
			cardZoneX6, cardZoneX7};
	
	//1 Zoom Zone
	final private float zoomZoneY = 0.408f;
	final private float zoomZoneX = 0.831f;
	
	private static Map<String, Map<Rectangle, ExtendedSprite>> zones;
	
	public BuilderArena(Texture texture) {
		super(texture);
		
		this.flip(false, true);
		this.firstResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setOrigin(0, 0);
		this.setPosition(0, 0);
	}
	
	private void firstResize(int x, int y) {
		
		//Set scale
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		
		zones = new LinkedHashMap<String, Map<Rectangle, ExtendedSprite>>();
		
		//Create zones
		Map<Rectangle, ExtendedSprite> deckZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> cardZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> zoomZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Put card rectangles into zones
		for(int i = 0; i < 7; i++) {
			cardZones.put(new Rectangle(x*cardZonesX[i], y*cardZoneY, x*cardZoneWidth, y*cardZoneHeight), null);
		}
		
		//Put deck rectangles into zones
		for(int i = 0; i < 10; i++) {
			for(int j= 0; j < 4; j++) {
				deckZones.put(new Rectangle(x*deckZonesX[i], y*deckZonesY[j], x*deckZoneWidth, y*deckZoneHeight), null);
			}
		}
		
		//Put spell rectangles into zones
		zoomZones.put(new Rectangle(x*zoomZoneX, y*zoomZoneY, x*zoomZoneWidth, y*zoomZoneHeight), null);
		
		//Add each map to zones map
		zones.put("cardZone", cardZones);
		zones.put("deckZone", deckZones);
		zones.put("zoomZone", zoomZones);
	}
	
	public void resize(int x, int y) {
		
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		//Adjust where zones are and stuff
		//Adjust Sprites that are currently in zones
		//TODO: this.resizeZones(x, y);
	}
	
	public static Map<String, Map<Rectangle, ExtendedSprite>> getMap() {
		return zones;
	}
	
}
