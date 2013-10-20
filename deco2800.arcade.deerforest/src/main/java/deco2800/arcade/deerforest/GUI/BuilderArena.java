package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
  	final private float deckZoneY1 = 0.84f; 
	final private float deckZoneY2 = 0.73f; 
	final private float deckZoneY3 = 0.62f; 
	final private float deckZoneY4 = 0.51f; 
	
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
	final private float cardZonesY = 0.28f;//constant for the card zones
	
	final private float cardZoneX1 = 0.235f;
	final private float cardZoneX2 = 0.297f;
	final private float cardZoneX3 = 0.359f;
	final private float cardZoneX4 = 0.421f;
	final private float cardZoneX5 = 0.483f;
	final private float cardZoneX6 = 0.545f;
	final private float cardZoneX7 = 0.607f;
	
	final private float[] cardZonesX = {cardZoneX1, cardZoneX2, cardZoneX3, 
			cardZoneX4, cardZoneX5, cardZoneX6, cardZoneX7};
	
	//1 Zoom Zone
	final private float zoomZoneY = 0.188f;
	final private float zoomZoneX = 0.831f;
	
	//Map to store all zones
	private static Map<String, Map<Rectangle, BuilderSprite>> zones;
	
	/**
	 * Sets the background
	 * 
	 * @param texture the background
	 */
	public BuilderArena(Texture texture) {
		super(texture);
		
		this.flip(false, true);
		this.firstResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setOrigin(0, 0);
		this.setPosition(0, 0);
	}
	
	/**
	 * Sets initial arena
	 * 
	 * @param int x for width
	 * @param int y for height
	 */
	private void firstResize(int x, int y) {	
		//Set scale
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		
		zones = new LinkedHashMap<String, Map<Rectangle, BuilderSprite>>();
		
		//Create zones
		Map<Rectangle, BuilderSprite> deckZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		Map<Rectangle, BuilderSprite> cardZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		Map<Rectangle, BuilderSprite> zoomZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		
		//Put card rectangles into zones
		for(int i = 0; i < 7; i++) {
			cardZones.put(new Rectangle(x*cardZonesX[i], y*cardZonesY, x*cardZoneWidth, y*cardZoneHeight), null);
		}
		
		//Put deck rectangles into zones
		for(int i = 0; i < 10; i++) {
			for(int j= 0; j < 4; j++) {
				deckZones.put(new Rectangle(x*deckZonesX[i], y*deckZonesY[j], x*deckZoneWidth, y*deckZoneHeight), null);
			}
		}
		
		//Put Zoom rectangle into zones
		zoomZones.put(new Rectangle(x*zoomZoneX, y*zoomZoneY, x*zoomZoneWidth, y*zoomZoneHeight), null);
		
		//Add each map to zones map
		zones.put("CardZone", cardZones);
		zones.put("DeckZone", deckZones);
		zones.put("ZoomZone", zoomZones);
	}
	
	/**
	 * Scales the arena to correct size
	 * 
	 * @param int x for width
	 * @param int y for height
	 */
	public void resize(int x, int y) {
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		this.resizeZones(x, y);
	}
	
	/**
	 * Gets the zones
	 * 
	 * @return A map of all the zones with strings as keys
	 */
	public static Map<String, Map<Rectangle, BuilderSprite>> getMap() {
		return zones;
	}
	
	/**
	 * Resizes the zones
	 * 
	 * @param x
	 * @param y
	 */
	private void resizeZones(int x, int y) {
		//create new cardZones zones
		Map<Rectangle, BuilderSprite> cardZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		
		//Resize currently placed sprites and rectangles
		int i = 0;
		for(Map.Entry<Rectangle, BuilderSprite> e : zones.get("CardZone").entrySet()) {
			//get this zones sprite
			BuilderSprite s = zones.get("CardZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*cardZonesX[i], y*cardZonesY, x*cardZoneWidth, y*cardZoneHeight);
			//insert zone
			cardZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}
		
		//create new p2Monster zones
		Map<Rectangle, BuilderSprite> deckZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		int j = 0;
		for(Map.Entry<Rectangle, BuilderSprite> e : zones.get("DeckZone").entrySet()) {
			//get this zones sprite
			if(i == 10) {
				i = 0;
				j++;
			}
			BuilderSprite s = zones.get("DeckZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*deckZonesX[i], y*deckZonesY[j], x*deckZoneWidth, y*deckZoneHeight);
			//insert zone
			deckZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}

		//create new p1HandZones
		Map<Rectangle, BuilderSprite> zoomZones = new LinkedHashMap<Rectangle, BuilderSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, BuilderSprite> e : zones.get("ZoomZone").entrySet()) {
			
			//get this zones sprite
			BuilderSprite s = zones.get("ZoomZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*zoomZoneX, y*zoomZoneY, x*zoomZoneWidth, y*zoomZoneHeight);
			//insert zone
			zoomZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}
		
		//clear zones list
		zones = new LinkedHashMap<String, Map<Rectangle, BuilderSprite>>();
		
		zones.put("CardZone", cardZones);
		zones.put("DeckZone", deckZones);
		zones.put("ZoomZone", zoomZones);
	}

	/**
	 * sets the size of the sprite to that of the rectangle containing it
	 * 
	 * @param r rectangle to scale to
	 * @param s sprite to scale
	 */
	private void setSpriteToRectangleSize(Rectangle r, BuilderSprite s) {
		float xScale = r.getWidth() / s.getWidth();
		float yScale = r.getHeight() / s.getHeight();
		float xPoint = r.getX();
		float yPoint = r.getY();
		s.scaleTo(xScale, yScale, timeToMove);
        s.moveTo(xPoint, yPoint, timeToMove);
	}
	
	/**
	 * Inserts a sprite in a given rectangle
	 * 
	 * @param s sprite to insert into rectangle
	 * @param r Rectangle which to insert the sprite into into
	 * 
	 * @return zone in which the sprite was inserted
	 */
	public String setSpriteToZone(BuilderSprite s, Rectangle r) {
		
		for(String key : zones.keySet()) {
			for(Rectangle zone : zones.get(key).keySet()) {
				if(zone.equals(r)) {
					zones.get(key).put(zone, s);
					setSpriteToRectangleSize(zone,s);
					return key;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the area of the point(eg. ZoomZone, DeckZone)
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return the name of the area
	 */
	public String getAreaAtPoint(int x, int y) {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		//Check if in a DeckZone
		if(width*deckZoneX1 <= x && width*(deckZoneX10+deckZoneWidth) >= x) {
			if(height*deckZoneY1 < y && (height*(deckZoneY4+deckZoneHeight)) > y) {
				return "DeckZone";
			}
		}
		//Check if in a CardZone
		if(width*cardZoneX1 <= x && width*(cardZoneX7+cardZoneWidth) >= x) {
			if(height*cardZonesY < y && (height*(cardZonesY+cardZoneHeight)) > y) {
				return "CardZone";
			}
		}
		
		//Check if in the ZoomZone
		if(width*zoomZoneX <= x && width*(zoomZoneX+zoomZoneWidth) >= x) {
			if(height*zoomZoneY < y && (height*(zoomZoneY+zoomZoneHeight)) > y) {
				return "ZoomZone";
			}
		}
		
		return null;
	}

	/**
	 * Prints out the zone info
	 */
	public void printZoneInfo() {
		for(String key : zones.keySet()) {
			for(Rectangle zone : zones.get(key).keySet()) {
				if(zones.get(key).get(zone) != null) {
					DeerForest.logger.info("key is: " + key + "Map is: " + zones.get(key).get(zone));
				}
			}
		}
	}
	
	/**
	 * Gets the available zones in specified area
	 * 
	 * @param whichArea - specifies the area to look for available rectangles 
	 * @return a list of available zones
	 */
	public List<Rectangle> getAvailableZones(String whichArea) {
		List<Rectangle> freeZones = new ArrayList<Rectangle>();
		
		Map<Rectangle, BuilderSprite> mapToCheck = null;
		
		if(whichArea == "DeckZone") {
			mapToCheck = zones.get("DeckZone");
		} else if(whichArea == "CardZone") {
			mapToCheck = zones.get("CardZone");
		} else if(whichArea == "ZoomZone") {
			mapToCheck = zones.get("ZoomZone");
		}
		
		for(Rectangle zone : mapToCheck.keySet()) {
			if(mapToCheck.get(zone) == null) {
				freeZones.add(zone);
			}
		}
		
		return freeZones;
	}
	
	/**
	 * Gets an empty rectangle to place card into
	 * 
	 * @param r 
	 * @param where which area to look in
	 * @return a rectangle that is empty 
	 */
	public Rectangle emptyZoneAtRectangle(Rectangle r, String where) {
		
		Map<Rectangle, BuilderSprite> mapToCheck = null;
		
		if(where == "DeckZone") {
			mapToCheck = zones.get("DeckZone");
		} else if(where == "CardZone") {
			mapToCheck = zones.get("CardZone");
		} else if(where == "ZoomZone") {
			mapToCheck = zones.get("ZoomZone");
		} 
		
		for(Rectangle zone : mapToCheck.keySet()) {
			//If no overlap then skip
			if(!zone.overlaps(r)) {
				continue;
			}
			//check amount of overlap
			double area = rectangleIntersectionArea(r, zone);
			
			if(area > r.getWidth()*r.getHeight()/3 || area > zone.getWidth()*zone.getHeight()/3 && mapToCheck.get(zone) == null) {
				return zone;
			}
		}
		return null;
	}
	
	/**
     * Gets the area of intersection between two rectangle
     *
     * @param r1 first rectangle
     * @param r2 second rectangle
     * @return the area of the rectangles that is overlapping
     */
	private double rectangleIntersectionArea(Rectangle r1, Rectangle r2) {
		double r1X = r1.getX();
		double r1Y = r1.getY();
		double r1Width = r1.getWidth();
		double r1Height = r1.getHeight();
		
		double r2X = r2.getX();
		double r2Y = r2.getY();
		double r2Width = r2.getWidth();
		double r2Height = r2.getHeight();
		
		double leftSide = (r1X>r2X)? r1X:r2X;
		double rightSide = (r1X+r1Width)<(r2X+r2Width)? r1X+r1Width:r2X+r2Width;
		double topSide = (r1Y>r2Y)? r1Y:r2Y;
		double bottomSide = (r1Y+r1Height)<(r2Y+r2Height)? r1Y+r1Height:r2Y+r2Height;
		
		return (rightSide - leftSide)*(bottomSide - topSide);
	}

	/**
	 * Removes a sprite form zones list
	 * @param s sprite to remove
	 * @return return true if successful
	 */
	public boolean removeSprite(BuilderSprite s) {
		for(String key : zones.keySet()) {
			for(Rectangle zone : zones.get(key).keySet()) {
				if(zones.get(key).get(zone) == s) {
					zones.get(key).put(zone, null);
                    return true;
				}
			}
		}
        return false;
	}
}
