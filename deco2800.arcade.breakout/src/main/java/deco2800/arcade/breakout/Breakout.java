package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * 
 * 
 * 
 */
@ArcadeGame(id="Breakout")

public class Breakout extends GameClient{
	
	private OrthographicCamera camera;
	private String player;
	private NetworkClient nc;
	//private int score;
	private Paddle paddle;
	
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	private Vector2 center = new Vector2(0f, SCREENWIDTH/2 - paddle.width/2);
	
	private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	
	//private GameState gameState;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	
	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		 this.player = player.getUsername();
		 this.nc = networkClient;
	}
	
	public void create() {
		super.create();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, SCREENWIDTH, SCREENHEIGHT);
		
		paddle = new LocalPlayer(new Vector2(center));
		
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
	}
	
	public void dispose(){
		super.dispose();
	}
	
	public void pause(){
		super.pause();
	}
	
	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    paddle.render(shapeRenderer);
	    
	    shapeRenderer.end();
	    
	    
	}


	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	
}


