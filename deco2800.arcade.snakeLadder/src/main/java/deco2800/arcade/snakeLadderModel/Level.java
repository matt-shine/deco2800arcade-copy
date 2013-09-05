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
		backgroundBoard = new Texture(Gdx.files.classpath("assets/board.png"));
		//initialise rule texture mapping
		ruleTextureMapping = new HashMap<String,String>();
		ruleTextureMapping.put("+","plus_10.png");
		ruleTextureMapping.put("*", "plus_20.png");
		ruleTextureMapping.put("-", "minus_10.png");
		ruleTextureMapping.put("/", "minus_20.png");
		ruleTextureMapping.put("#", "stop.png");
	}
	
	public void renderMap(List<Tile> tileList, SpriteBatch batch)
	{
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(!t.getRule().equals("."))
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}	   
	}
	
	public abstract boolean loadMap(List<Tile> tileList, String filePath);

}
