package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

//This class functions basically as the controller
public class MainInputProcessor implements InputProcessor {

	private MainGame game;
	private MainGameScreen view;
	
	public MainInputProcessor(MainGame game, MainGameScreen view) {
		this.game = game;
		this.view = view;
	}
	
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key: " + arg0 + " was pressed");
		if(arg0 == Keys.F) {
			System.out.println("Game: " + game + " view: " + view);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key: " + arg0 + " was typed");
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("Key: " + arg0 + " was released");
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		System.out.println("Mouse moved to: " + arg0 + ", " + arg1);
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("Scrolled in direction: " + arg0);
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		System.out.println("Mouse click at: " + arg0 + ", " + arg1 + " with extra params: " + arg2 + " " + arg3);
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("Touch dragged");
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		System.out.println("Mouse released at: " + arg0 + ", " + arg1 + " with extra params: " + arg2 + " " + arg3);
		return false;
	}

}
