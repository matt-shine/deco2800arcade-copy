package deco2800.arcade.snakeLadderModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Level {
	
	protected HashMap<String,String> ruleTextureMapping;
	protected Texture backgroundBoard;
	
	public void ini()
	{
		//loading of background game board
		backgroundBoard = new Texture(Gdx.files.classpath("images/board.png"));
		//initialise rule texture mapping
		ruleTextureMapping = new HashMap<String,String>();
		ruleTextureMapping.put("+10","plus_10.png");
		ruleTextureMapping.put("+20", "plus_20.png");
		ruleTextureMapping.put("+50", "plus_50.png");
		ruleTextureMapping.put("+100", "plus_100.png");
		ruleTextureMapping.put("-10", "minus_10.png");
		ruleTextureMapping.put("-20", "minus_20.png");
		ruleTextureMapping.put("-50", "minus_50.png");
		ruleTextureMapping.put("-100", "minus_100.png");
		ruleTextureMapping.put("#", "stop.png");
	}
	
	public void renderMap(Tile[] tileList, SpriteBatch batch)
	{
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(!t.getRule().equals(".")&&!t.getRule().equals("S")&&!t.getRule().equals("E"))
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}	   
	}
	
	public abstract Tile[] iniMapSize();
	
	public abstract boolean loadMap(Tile[] tileList, String filePath);

}
