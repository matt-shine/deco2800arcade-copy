package deco2800.arcade.snakeLadder;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.snakeLadderModel.*;

/**
 * This is the main class for game snake&Ladder
 * @author s4310055
 *
 */

@ArcadeGame(id="snakeLadder")
public class SnakeLadder extends GameClient {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture backgroundBoard;
	private List<Tile> tileList; 
	private HashMap<Character,String> ruleTextureMapping;
	private Texture player;

	public SnakeLadder(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		//add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				//TODO: unpause pong
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void show() {
				//TODO: unpause pong
			}
        });
        
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 800);
		batch = new SpriteBatch();
		
		//loading of background game board
		backgroundBoard = new Texture(Gdx.files.classpath("assets/board.png"));
		player =new Texture(Gdx.files.classpath("assets/player.png"));
		
		//initialise rule texture mapping
		ruleTextureMapping = new HashMap<Character,String>();
		ruleTextureMapping.put('+',"plus_10.png");
		ruleTextureMapping.put('*', "plus_20.png");
		ruleTextureMapping.put('-', "minus_10.png");
		ruleTextureMapping.put('/', "minus_20.png");
		ruleTextureMapping.put('#', "stop.png");
		
		//loading game map
		tileList = new ArrayList<Tile>();
		loadMap(tileList,"assets/lvl1.txt");
		System.out.println("success");
	}
	

	@Override
	public void render() {
		
		// tell the camera to update its matrices.
		camera.update();
  
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(t.getRule()!='.')
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}
		batch.draw(player,0,600);
		
		batch.end();
   
		
		super.render();
		
	}
	
	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width*2, height*2);
	}

	private boolean loadMap(List<Tile> tileList, String filePath) {
		FileHandle handle = Gdx.files.classpath(filePath);
		BufferedReader file = handle.reader(2048);
		
		int counter = 0;
		try {
			String line = "";
			while((line = file.readLine()) != null)
			{
				for(int i=0;i<line.length();i++)
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
					Tile t = new TileLvl1(index,60,line.charAt(i));
					if(t.getRule()!='.')
					{
						t.setTexture(new Texture(Gdx.files.classpath("assets/"+ruleTextureMapping.get(t.getRule()))));
					}
					tileList.add(t);
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
