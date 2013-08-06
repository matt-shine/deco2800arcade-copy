package deco2800.arcade.arcadeui;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;

/**
 * This class is the main interface for the arcade. It can be run as a game,
 * but normally it will be run in parallel with another game as an overlay.
 * @author Simon
 *
 */
@ArcadeGame(id="arcadeui")
public class ArcadeUI extends GameClient {
	   
	private ShapeRenderer shapeRenderer;
	private boolean isOverlay = false;
	
	public ArcadeUI(Player player, NetworkClient networkClient, Boolean isOverlay){
		super(player, networkClient);
		this.isOverlay = isOverlay;
	}
	
	public ArcadeUI(Player player, NetworkClient networkClient){
		this(player, networkClient, false);
	}

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		System.out.println("at ui create");
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    shapeRenderer.filledRect(100,
            100,
            100,
            100);
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	}
	
	@Override
	public void dispose() {
		System.out.println("at ui die");
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
	//there are no achievements for this
	private static Set<Achievement> achievements = new HashSet<Achievement>();


	private static final Game game;
	static {
		game = new Game();
		game.gameId = "arcadeui";
		game.name = "Arcade UI";
		game.availableAchievements = achievements;
	}

	public Game getGame() {
		return game;
	}
		
}
