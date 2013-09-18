package deco2800.arcade.snakeLadderModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {
	
	private HashMap<String,String> ruleTextureMapping;
	private Texture backgroundBoard;
	private Tile[] tileList = new Tile[100]; 
	public Tile[] getTileList() {
		return tileList;
	}

	public void setTileList(Tile[] tileList) {
		this.tileList = tileList;
	}

	private List<Ladder> ladderList = new ArrayList<Ladder>();
	
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
	
	public boolean loadMap(String filePath) {		
		//reading file
		FileHandle handle = Gdx.files.classpath(filePath);
		BufferedReader file = handle.reader(2048);
		
		int counter = 0;
		try {
			String line = "";
			while((line = file.readLine()) != null)
			{
				String[] tiles = line.split(";");
				for(int i=0;i<tiles.length;i++)
				{
					int index =1;
					if(counter%2==0)
					{
						index = (10-counter)*10-i;
					}
					else
					{
						index = (10-counter-1)*10+i+1;
					}
					Tile t = new Tile(index,60,tiles[i]);
					if(t.getRule().startsWith("L"))
					{
						Ladder l = new Ladder(index, (int)Integer.parseInt(t.getRule().substring(1)));
						ladderList.add(l);
					}
					else if(!t.getRule().equals("."))
					{
						t.setTexture(new Texture(Gdx.files.classpath("images/"+ruleTextureMapping.get(t.getRule()))));
					}
					tileList[index-1]=t;
				}
				counter++;
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//initialize ladder rotation and scalling 
		for(Ladder l: ladderList)
		{
			l.createLadder(tileList);
		}
		return true;
	}
	
	public void renderMap(SpriteBatch batch)
	{
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(t.getRule().startsWith("L"))
			{
				for(Ladder l: ladderList)
				{
					l.getSprite().draw(batch);
				}
			}
			else if(!t.getRule().equals(".")&&!t.getRule().equals("S")&&!t.getRule().equals("E"))
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}	   
	}
}
