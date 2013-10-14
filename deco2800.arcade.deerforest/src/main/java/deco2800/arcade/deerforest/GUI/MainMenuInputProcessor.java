package deco2800.arcade.deerforest.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;

//This class functions basically as the controller
public class MainMenuInputProcessor implements InputProcessor {

	private MainMenu game;
	private MainMenuScreen view;
	
	public MainMenuInputProcessor(MainMenu game, MainMenuScreen view) {
		this.game = game;
		this.view = view;
	}

	@Override
	public boolean keyDown(int keycode) {
		
        if(keycode == Keys.NUM_0) {
            DeerForestSingletonGetter.getDeerForest().changeScreen("game");
        }
        
        if(keycode == Keys.NUM_1) {
        	DeerForestSingletonGetter.getDeerForest().changeScreen("deck builder");
        }
        return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
