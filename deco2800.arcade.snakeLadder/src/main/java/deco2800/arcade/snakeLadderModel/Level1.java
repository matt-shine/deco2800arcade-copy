package deco2800.arcade.snakeLadderModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Level1 extends Level {
	
	@Override
	public Tile[] iniMapSize()
	{
		return new Tile[100];
	}

	@Override
	public boolean loadMap(Tile[] tileList, String filePath) {
//		//initialize arraylist with size x
//		tileList = new Tile[gameboardSize];
		
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
					int index =0;
					if(counter%2==0)
					{
						index = (10-counter)*10-i;
					}
					else
					{
						index = (10-counter-1)*10+i+1;
					}
					Tile t = new TileLvl1(index,60,tiles[i]);
					if(!t.getRule().equals("."))
					{
						t.setTexture(new Texture(Gdx.files.classpath("assets/"+ruleTextureMapping.get(t.getRule()))));
					}
					tileList[index-1]=t;
				}
				counter++;
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
