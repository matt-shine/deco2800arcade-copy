package deco2800.arcade.deerforest.GUI;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

//This class functions basically as the controller
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	private ExtendedSprite currentSelection;
	private float xClickOffset;
	private float yClickOffset;
	
	private final float scale = 0.25f;
	
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		if(keycode == Keys.SPACE) {
			if(currentSelection != null) {
				if (currentSelection.getScaleX() > scale || currentSelection.getScaleY() > scale) {
					currentSelection.setScale(scale);
				} else {
					currentSelection.setScale(scale*2);
				}
			}
		}
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
    	
    	if(currentSelection != null && view.getArena().emptyZoneAtPoint(x, y, 0, false) != null) {
    		view.getArena().removeSprite(currentSelection);
    		view.getArena().setSpriteToZone(currentSelection, view.getArena().emptyZoneAtPoint(x, y, 0, false), 0, false);
    		return true;
    	}
    	currentSelection = checkIntersection(x, y);
    	if(currentSelection != null) {
    		xClickOffset = x - currentSelection.getX();
    		yClickOffset = y - currentSelection.getY();
    		return true;
    	}
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
    	if(currentSelection != null && view.getArena().emptyZoneAtRectangle(currentSelection.getBoundingRectangle(), 0, false) != null) {
    		view.getArena().removeSprite(currentSelection);
    		view.getArena().setSpriteToZone(currentSelection, view.getArena().emptyZoneAtRectangle(currentSelection.getBoundingRectangle(), 0, false), 0, false);
    		return true;
    	}
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
    	if(currentSelection != null) {
    		view.getArena().removeSprite(currentSelection);
    		currentSelection.setPosition(x - xClickOffset, y - yClickOffset);
    	}
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }

    /**
     * returns the sprite at the point, if one exists
     * 
     * @param x
     * @param y
     * @return Sprite intersecting the 
     */
    private ExtendedSprite checkIntersection(int x, int y) {
    	Map<String,List<ExtendedSprite>> spriteMap = view.getSpriteMap();
    	for(String key : spriteMap.keySet()) {
	    	for(ExtendedSprite s : spriteMap.get(key)) {
		    	if(s.containsPoint(x, y)) {
		    		return s;
		    	}
		    }
	    }

    	return null;
    }
}
