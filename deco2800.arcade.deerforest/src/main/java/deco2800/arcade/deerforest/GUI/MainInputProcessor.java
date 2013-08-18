package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.InputProcessor;

//This class functions basically as the controller
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	private ExtendedSprite currentSelection;
	
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
	}
	
	@Override
	public boolean keyDown (int keycode) {
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
    	currentSelection = checkIntersection(x, y);
    	if(currentSelection != null) {
    		return true;
    	}
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
    	currentSelection = null;
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
    	if(currentSelection != null) {
    		if(currentSelection == view.getS1()) {
    			view.setS1(x, y);
    		}
    		if(currentSelection == view.getS2()) {
    			view.setS2(x, y);
    		}
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

    	if(x > view.getS1Position()[0] && x < (view.getS1Position()[0] + view.getS1().getWidth()) && y > view.getS1Position()[1] && y < (view.getS1Position()[1] + view.getS1().getHeight())) {
    		return view.getS1();
    	}
    	if(x > view.getS2Position()[0] && x < (view.getS2Position()[0] + view.getS2().getWidth()) && y > view.getS2Position()[1] && y < (view.getS2Position()[1] + view.getS2().getHeight())) {
    		return view.getS2();
    	}
    	return null;
    }
}
