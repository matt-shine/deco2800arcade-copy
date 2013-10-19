package deco2800.arcade.snakeLadderModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {
	private Texture backgroundBoard;
	private Tile[] tileList = new Tile[100]; 
	
	//This is the empty constructor for testing
	public GameMap(){}
	
	public GameMap(FileHandle file)
	{
		this.backgroundBoard = new Texture(file);
	}
	
	public Tile[] getTileList() {
		return tileList;
	}

	public void setTileList(Tile[] tileList) {
		this.tileList = tileList;
	}

	private List<SnakeLadderBridge> snakeLadderList = new ArrayList<SnakeLadderBridge>();
	
	public boolean loadMap(FileHandle handle, HashMap<String,RuleMapping> ruleMapping) {
		try {
			populateTileListFromMapFile(handle, ruleMapping);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//loading textures for plugin rules
		for(Tile t:tileList)
		{
			if(!(t.getRule().equals(".")||t.getRule().startsWith("L")||t.getRule().startsWith("S")))
			{
				t.setTexture(new Texture(Gdx.files.classpath("images/"+ ruleMapping.get(t.getRule()).getIcon())));
			}
		}
		//initialize ladder rotation and scalling 
		for(SnakeLadderBridge l: snakeLadderList)
		{
			l.createLadder(tileList);
		}
		return true;
	}

	/**
	 * @param handle
	 * @param ruleMapping
	 * @throws NumberFormatException
	 */
	public void populateTileListFromMapFile(FileHandle handle,
			HashMap<String, RuleMapping> ruleMapping)
			throws NumberFormatException,IOException,Exception {
		BufferedReader file = handle.reader(2048);
		int counter = 0;
		try {
			String line = "";
			while((line = file.readLine()) != null)
			{
				String[] tileRules = line.split(";");
				for(int i=0;i<tileRules.length;i++)
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
					Tile t = new Tile(index,tileRules[i]);
					if(t.getRule().startsWith("L"))
					{
						SnakeLadderBridge sl = new SnakeLadderBridge(index, (int)Integer.parseInt(t.getRule().substring(1)),true);
						snakeLadderList.add(sl);
					}
					else if(t.getRule().startsWith("S"))
					{
						SnakeLadderBridge sl = new SnakeLadderBridge(index, (int)Integer.parseInt(t.getRule().substring(1)),false);
						snakeLadderList.add(sl);
					}
//					else if(!t.getRule().equals("."))
//					{
//						t.setTexture(new Texture(Gdx.files.classpath("images/"+ ruleMapping.get(t.getRule()).getIcon())));
//					}
					tileList[index-1]=t;
				}
				counter++;
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void renderMap(SpriteBatch batch)
	{
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(t.getRule().startsWith("L")||t.getRule().startsWith("S"))
			{
				for(SnakeLadderBridge sl: snakeLadderList)
				{
					sl.getSprite().draw(batch);
				}
			}
			else if(!t.getRule().equals("."))
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}	   
	}
}
