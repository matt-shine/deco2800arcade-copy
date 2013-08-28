package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Arena extends Sprite {
	
	//Zone widths / heights (ratio to window size)
	final private float monsterZoneWidth = 0.09844f;
	final private float monsterZoneHeight = 0.17708f;
	final private float handZoneWidth = 0.078125f;
	final private float handZoneHeight = 0.141666f;
	final private float spellZoneWidth = 0.08125f;
	final private float spellZoneHeight = 0.145833f;
	
	//Zone points (ratio to window size)
	
	//Monster zones
	final private float P1MonsterZoneY = 0.51667f; //constant for all P1 monster zones
	final private float P2MonsterZoneY = 0.3f; //constant for all P2 monster zones
	
	final private float MonsterZone1X = 0.22969f; //constant for all Zone 1 monsters
	final private float MonsterZone2X = 0.3421875f; //constant for all Zone 2 monsters
	final private float MonsterZone3X = 0.4578f; //constant for all Zone 3 monsters
	final private float MonsterZone4X = 0.5703f; //constant for all Zone 4 monsters
	final private float MonsterZone5X = 0.6828f; //constant for all Zone 5 monsters
	
	//Spell zones
	final private float P1SpellZone1Y = 0.5916667f; 
	final private float P1SpellZone2Y = 0.6354167f;
	final private float P2SpellZone1Y = 0.2166667f;
	final private float P2SpellZone2Y = 0.2625f;
	
	final private float SpellZone1X = 0.084375f;
	final private float SpellZone2X = 0.128125f;
	
	//Hand zones
	final private float P1HandZoneY = 0.8270833f;
	final private float P2HandZoneY = 0.0333333f;
	
	final private float HandZone1X = 0.3125f;
	final private float HandZone2X = 0.40625f;
	final private float HandZone3X = 0.5f;
	final private float HandZone4X = 0.59375f;
	final private float HandZone5X = 0.6875f;
	final private float HandZone6X = 0.78125f;
	
	private Map<String, Map<Rectangle, ExtendedSprite>> zones;
	
	public Arena(Texture texture) {
		super(texture);
		zones = new HashMap<String, Map<Rectangle, ExtendedSprite>>();

		this.flip(false, true);
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		//clear zones list
		zones = new HashMap<String, Map<Rectangle, ExtendedSprite>>();
		
		//create new p1Monster zones
		Map<Rectangle, ExtendedSprite> p1MonsterZones = new HashMap<Rectangle, ExtendedSprite>();
		p1MonsterZones.put(new Rectangle(x*MonsterZone1X, y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p1MonsterZones.put(new Rectangle(x*MonsterZone2X, y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p1MonsterZones.put(new Rectangle(x*MonsterZone3X, y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p1MonsterZones.put(new Rectangle(x*MonsterZone4X, y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p1MonsterZones.put(new Rectangle(x*MonsterZone5X, y*P1MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		
		//create new p2Monster zones
		Map<Rectangle, ExtendedSprite> p2MonsterZones = new HashMap<Rectangle, ExtendedSprite>();
		p2MonsterZones.put(new Rectangle(x*MonsterZone1X, y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p2MonsterZones.put(new Rectangle(x*MonsterZone2X, y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p2MonsterZones.put(new Rectangle(x*MonsterZone3X, y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p2MonsterZones.put(new Rectangle(x*MonsterZone4X, y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		p2MonsterZones.put(new Rectangle(x*MonsterZone5X, y*P2MonsterZoneY, x*monsterZoneWidth, y*monsterZoneHeight), null);
		
		//create new p1Spell zones
		Map<Rectangle, ExtendedSprite> p1SpellZones = new HashMap<Rectangle, ExtendedSprite>();
		p1SpellZones.put(new Rectangle(x*SpellZone1X, y*P1SpellZone1Y, x*spellZoneWidth, y*spellZoneHeight), null);
		p1SpellZones.put(new Rectangle(x*SpellZone2X, y*P1SpellZone2Y, x*spellZoneWidth, y*spellZoneHeight), null);
		
		//create new p1Spell zones
		Map<Rectangle, ExtendedSprite> p2SpellZones = new HashMap<Rectangle, ExtendedSprite>();
		p2SpellZones.put(new Rectangle(x*SpellZone1X, y*P2SpellZone1Y, x*spellZoneWidth, y*spellZoneHeight), null);
		p2SpellZones.put(new Rectangle(x*SpellZone2X, y*P2SpellZone2Y, x*spellZoneWidth, y*spellZoneHeight), null);
		
		//create new p1HandZones
		Map<Rectangle, ExtendedSprite> p1HandZones = new HashMap<Rectangle, ExtendedSprite>();
		p1HandZones.put(new Rectangle(x*HandZone1X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p1HandZones.put(new Rectangle(x*HandZone2X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p1HandZones.put(new Rectangle(x*HandZone3X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p1HandZones.put(new Rectangle(x*HandZone4X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p1HandZones.put(new Rectangle(x*HandZone5X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p1HandZones.put(new Rectangle(x*HandZone6X, y*P1HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		
		//create new p1HandZones
		Map<Rectangle, ExtendedSprite> p2HandZones = new HashMap<Rectangle, ExtendedSprite>();
		p2HandZones.put(new Rectangle(x*HandZone1X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p2HandZones.put(new Rectangle(x*HandZone2X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p2HandZones.put(new Rectangle(x*HandZone3X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p2HandZones.put(new Rectangle(x*HandZone4X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p2HandZones.put(new Rectangle(x*HandZone5X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		p2HandZones.put(new Rectangle(x*HandZone6X, y*P2HandZoneY, x*handZoneWidth, y*handZoneHeight), null);
		
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
		s.setScale(xScale, yScale);
		s.setPosition(xPoint, yPoint);
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

	public void removeSprite(ExtendedSprite s) {
		for(String key : zones.keySet()) {
			for(Rectangle zone : zones.get(key).keySet()) {
				if(zones.get(key).get(zone) == s) {
					zones.get(key).put(zone, null);
				}
			}
		}
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
	
}
