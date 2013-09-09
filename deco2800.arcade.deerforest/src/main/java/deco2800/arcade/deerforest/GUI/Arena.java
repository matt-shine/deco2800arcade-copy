package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Arena extends Sprite {

    //time time to move / scale sprites
    final private int timeToMove = 20;
	
	//Zone widths / heights (ratio to window size)
	final private float monsterZoneWidth = 0.0738095f;
	final private float monsterZoneHeight = 0.1407185f;
	final private float handZoneWidth = 0.06093755f;
	final private float handZoneHeight = 0.1127744f;
	final private float spellZoneWidth = 0.06093749f;
	final private float spellZoneHeight = 0.11388894f;
	
	//Zone points (ratio to window size)
	
	//Monster zones
	final private float P1MonsterZoneY = 0.5419162f; //constant for all P1 monster zones
	final private float P2MonsterZoneY = 0.36327344f; //constant for all P2 monster zones
	
	final private float MonsterZone1X = 0.32440478f; //constant for all Zone 1 monsters
	final private float MonsterZone2X = 0.41309524f; //constant for all Zone 2 monsters
	final private float MonsterZone3X = 0.5005952f; //constant for all Zone 3 monsters
	final private float MonsterZone4X = 0.58928573f; //constant for all Zone 4 monsters
	final private float MonsterZone5X = 0.6785714f; //constant for all Zone 5 monsters
	final private float[] MonsterZonesX = {MonsterZone1X, MonsterZone2X, MonsterZone3X, MonsterZone4X, MonsterZone5X};
	
	//Spell zones
	final private float P1SpellZone1Y = 0.56527776f; 
	final private float P1SpellZone2Y = 0.5833333f;
	final private float P2SpellZone1Y = 0.35972223f;
	final private float P2SpellZone2Y = 0.34166667f;
	final private float[] P1SpellZonesY = {P1SpellZone1Y, P1SpellZone2Y};
	final private float[] P2SpellZonesY = {P2SpellZone1Y, P2SpellZone2Y};
	
	final private float SpellZone1X = 0.19140625f;
	final private float SpellZone2X = 0.240625f;
	final private float[] SpellZonesX = {SpellZone1X, SpellZone2X};
	
	//Hand zones
	final private float P1HandZoneY = 0.7714571f;
	final private float P2HandZoneY = 0.17964073f;
	
	final private float HandZone1X = 0.32023808f;
	final private float HandZone2X = 0.39285713f;
	final private float HandZone3X = 0.46547619f;
	final private float HandZone4X = 0.53869045f;
	final private float HandZone5X = 0.61071426f;
	final private float HandZone6X = 0.68273807f;
	final private float[] HandZonesX = {HandZone1X, HandZone2X, HandZone3X, HandZone4X, HandZone5X, HandZone6X};
	
	private Map<String, Map<Rectangle, ExtendedSprite>> zones;
	
	public Arena(Texture texture) {
		super(texture);
		
		this.flip(false, true);
		this.firstResize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.setOrigin(0, 0);
		this.setPosition(0, 0);
	}

	public void resize(int x, int y) {
		
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		//Adjust where zones are and stuff
		//Adjust Sprites that are currently in zones
		this.resizeZones(x, y);
	}
	
	private void resizeZones(int x, int y) {

		//create new p1Monster zones
		Map<Rectangle, ExtendedSprite> p1MonsterZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		int i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P1MonsterZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P1MonsterZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*MonsterZonesX[i], y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight);
			//insert zone
			p1MonsterZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}
		
		//create new p2Monster zones
		Map<Rectangle, ExtendedSprite> p2MonsterZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P2MonsterZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P2MonsterZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*MonsterZonesX[i], y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight);
			//insert zone
			p2MonsterZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}

		//create new p1HandZones
		Map<Rectangle, ExtendedSprite> p1HandZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P1HandZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P1HandZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*HandZonesX[i], y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight);
			//insert zone
			p1HandZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}

		//create new p2HandZones
		Map<Rectangle, ExtendedSprite> p2HandZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P2HandZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P2HandZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*HandZonesX[i], y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight);
			//insert zone
			p2HandZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}


		//create new p1Spell zones
		Map<Rectangle, ExtendedSprite> p1SpellZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P1SpellZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P1SpellZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*SpellZonesX[i], y*P1SpellZonesY[i], x*spellZoneWidth, y*spellZoneHeight);
			//insert zone
			p1SpellZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}

		//create new p2Spell zones
		Map<Rectangle, ExtendedSprite> p2SpellZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Resize currently placed sprites and rectangles
		i = 0;
		for(Map.Entry<Rectangle, ExtendedSprite> e : zones.get("P2SpellZone").entrySet()) {
			//get this zones sprite
			ExtendedSprite s = zones.get("P2SpellZone").get(e.getKey());
			//define new rectangle to insert
			Rectangle r2 = new Rectangle(x*SpellZonesX[i], y*P2SpellZonesY[i], x*spellZoneWidth, y*spellZoneHeight);
			//insert zone
			p2SpellZones.put(r2, s);
			//if sprite was at zone, set it to new size
			if(s != null) setSpriteToRectangleSize(r2, s);
			//increment counter
			i++;
		}
		
		//clear zones list
		zones = new LinkedHashMap<String, Map<Rectangle, ExtendedSprite>>();
		
		zones.put("P1MonsterZone", p1MonsterZones);
		zones.put("P2MonsterZone", p2MonsterZones);
		zones.put("P1SpellZone", p1SpellZones);
		zones.put("P2SpellZone", p2SpellZones);
		zones.put("P1HandZone", p1HandZones);
		zones.put("P2HandZone", p2HandZones);
	}

	private void firstResize(int x, int y) {
		
		//Set scale
		float xScale = x / this.getWidth();
		float yScale = y / this.getHeight();
		this.setScale(xScale, yScale);
		
		zones = new LinkedHashMap<String, Map<Rectangle, ExtendedSprite>>();
		
		//Create zones
		Map<Rectangle, ExtendedSprite> p1MonsterZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> p2MonsterZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> p1SpellZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> p2SpellZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> p1HandZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		Map<Rectangle, ExtendedSprite> p2HandZones = new LinkedHashMap<Rectangle, ExtendedSprite>();
		
		//Put monster rectangles into zones
		for(int i = 0; i < 5; i++) {
			p1MonsterZones.put(new Rectangle(x*MonsterZonesX[i], y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
			p2MonsterZones.put(new Rectangle(x*MonsterZonesX[i], y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		}
		
		//Put hand rectangles into zones
		for(int i = 0; i < 6; i++) {
			p1HandZones.put(new Rectangle(x*HandZonesX[i], y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
			p2HandZones.put(new Rectangle(x*HandZonesX[i], y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		}
		
		//Put spell rectangles into zones
		p1SpellZones.put(new Rectangle(x*SpellZone1X, y*P1SpellZone1Y, x*spellZoneWidth, y*spellZoneHeight), null);
		p1SpellZones.put(new Rectangle(x*SpellZone2X, y*P1SpellZone2Y, x*spellZoneWidth, y*spellZoneHeight), null);
		p2SpellZones.put(new Rectangle(x*SpellZone1X, y*P2SpellZone1Y, x*spellZoneWidth, y*spellZoneHeight), null);
		p2SpellZones.put(new Rectangle(x*SpellZone2X, y*P2SpellZone2Y, x*spellZoneWidth, y*spellZoneHeight), null);
		
		//Add each map to zones map
		zones.put("P1MonsterZone", p1MonsterZones);
		zones.put("P2MonsterZone", p2MonsterZones);
		zones.put("P1SpellZone", p1SpellZones);
		zones.put("P2SpellZone", p2SpellZones);
		zones.put("P1HandZone", p1HandZones);
		zones.put("P2HandZone", p2HandZones);
	}
	
	/**
	 * Returns the zone at point x,y if one exists that belongs to given player
	 * and is either a field (true) or hand (false) zone as well as being empty
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Rectangle emptyZoneAtPoint(int x, int y, int player, boolean field, boolean monster) {
		double leftSide;
		double rightSide;
		double topSide;
		double bottomSide;
		
		Map<Rectangle, ExtendedSprite> mapToCheck;
		
		if(player == 1 && field && monster) {
			mapToCheck = zones.get("P1MonsterZone");
		} else if(player == 1 && field && !monster) {
			mapToCheck = zones.get("P1SpellZone");
		} else if(player == 1) {
			mapToCheck = zones.get("P1HandZone");
		} else if(player == 2 && field && monster) {
			mapToCheck = zones.get("P2MonsterZone");
		} else if(player == 2 && field && !monster) {
			mapToCheck = zones.get("P2SpellZone");
		} else {
			mapToCheck = zones.get("P2HandZone");
		}
		
		for(Rectangle r : mapToCheck.keySet()) {
			leftSide = r.getX();
			rightSide = r.getX() + r.getWidth();
			topSide = r.getY();
			bottomSide = r.getY() + r.getHeight();
			
			if(x > leftSide && x < rightSide && y > topSide && y < bottomSide && mapToCheck.get(r) == null) {
				return r;
			}
		}
		
		return null;
	}
	
	/**
	 *Returns zone which overlaps (50%) of r or zone, if one exists that belongs to player and
	 *is either a field(true) or hand (false) zone as well as being empty
	 * 
	 * @param r
	 * @return
	 */
	public Rectangle emptyZoneAtRectangle(Rectangle r, int player, boolean field, boolean monster) {
		
		Map<Rectangle, ExtendedSprite> mapToCheck;
		
		if(player == 1 && field && monster) {
			mapToCheck = zones.get("P1MonsterZone");
		} else if(player == 1 && field && !monster) {
			mapToCheck = zones.get("P1SpellZone");
		} else if(player == 1) {
			mapToCheck = zones.get("P1HandZone");
		} else if(player == 2 && field && monster) {
			mapToCheck = zones.get("P2MonsterZone");
		} else if(player == 2 && field && !monster) {
			mapToCheck = zones.get("P2SpellZone");
		} else {
			mapToCheck = zones.get("P2HandZone");
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
	
	//Sets the sprite to fit within the given rectangle
	//Returns the area that the sprite was set to
	public String setSpriteToZone(ExtendedSprite s, Rectangle r, int player) {
		
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
	
	private void setSpriteToRectangleSize(Rectangle r, ExtendedSprite s) {
		float xScale = r.getWidth() / s.getWidth();
		float yScale = r.getHeight() / s.getHeight();
		float xPoint = r.getX();
		float yPoint = r.getY();
		s.scaleTo(xScale, yScale, timeToMove);
        s.moveTo(xPoint, yPoint, timeToMove);
	}

	public List<Rectangle> getAvailableZones(int player, boolean field, boolean monsters) {
		List<Rectangle> freeZones = new ArrayList<Rectangle>();
		
		Map<Rectangle, ExtendedSprite> mapToCheck;
		
		if(player == 1 && field && monsters) {
			mapToCheck = zones.get("P1MonsterZone");
		} else if(player == 1 && field && !monsters) {
			mapToCheck = zones.get("P1SpellZone");
		} else if(player == 1) {
			mapToCheck = zones.get("P1HandZone");
		} else if(player == 2 && field && monsters) {
			mapToCheck = zones.get("P2MonsterZone");
		} else if(player == 2 && field && !monsters) {
			mapToCheck = zones.get("P2SpellZone");
		} else {
			mapToCheck = zones.get("P2HandZone");
		}
		
		for(Rectangle zone : mapToCheck.keySet()) {
			if(mapToCheck.get(zone) == null) {
				freeZones.add(zone);
			}
		}
		
		return freeZones;
	}

	public boolean removeSprite(ExtendedSprite s) {
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
	
	public void printZoneInfo() {
		for(String key : zones.keySet()) {
			for(Rectangle zone : zones.get(key).keySet()) {
				if(zones.get(key).get(zone) != null) {
					System.out.println("key is: " + key + "Map is: " + zones.get(key).get(zone));
				}
			}
		}
	}
	
	//Returns the zone (p1hand, p2field, etc) that the point lies inside 
	public String getAreaAtPoint(int x, int y) {
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		//Check if in a monster Zone
		if(width*MonsterZone1X <= x && width*(MonsterZone5X+monsterZoneWidth) >= x) {
			//check what player
			if(height*P1MonsterZoneY < y && (height*(P1MonsterZoneY+monsterZoneHeight)) > y) {
				return "P1MonsterZone";
			} else if(height*P2MonsterZoneY < y && (height*(P2MonsterZoneY+monsterZoneHeight)) > y) {
				return "P2MonsterZone";
			}
		}
		//Check if in a monster Zone
		if(width*HandZone1X <= x && width*(HandZone6X+handZoneWidth) >= x) {
			//check what player
			if(height*P1HandZoneY < y && (height*(P1HandZoneY+handZoneHeight)) > y) {
				return "P1HandZone";
			} else if(height*P2HandZoneY < y && (height*(P2HandZoneY+handZoneHeight)) > y) {
				return "P2HandZone";
			}
		}
		//Check if in a spell zone
		if(width*SpellZone1X <= x && width*(SpellZone2X+spellZoneWidth) >= x) {
			//check what player
			if(height*P1SpellZone1Y < y && (height*(P1SpellZone2Y+spellZoneHeight)) > y) {
				return "P1SpellZone";
			} else if(height*P2SpellZone1Y < y && (height*(P2SpellZone2Y+spellZoneHeight)) > y) {
				return "P2SpellZone";
			}
		}
		
		return null;
	}

    public List<Rectangle> getFilledMonsterZones(int player) {

        List<Rectangle> filledZones = new ArrayList<Rectangle>();

        Map<Rectangle, ExtendedSprite> mapToCheck;

        if(player == 1) {
            mapToCheck = zones.get("P1MonsterZone");
        } else {
            mapToCheck = zones.get("P2MonsterZone");
        }

        for(Rectangle zone : mapToCheck.keySet()) {
            if(mapToCheck.get(zone) != null) {
                filledZones.add(zone);
            }
        }

        return filledZones;
    }

}
