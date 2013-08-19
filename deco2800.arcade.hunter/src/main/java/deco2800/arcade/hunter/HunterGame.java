package deco2800.arcade.hunter;

import java.util.HashSet;
import java.util.Set;

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
 * A Hunter game for use in the Arcade
 * @author Nessex
 *
 */
public class HunterGame {
	/* Make these all derived from the same class TODO */
	private LayerOne layerOne = new LayerOne();
	private LayerTwo layerTwo = new LayerTwo();
	private LayerThree layerThree = new LayerThree();
	private Foreground foreground = new Foreground();

	private enum Event = {
		ENTITY_COLLISION
	};

	private class GameEvent {
		int type;
		String arguments[];
		public GameEvent(int eventType, Array<String> args) {
			type = eventType;
			arguments = args;
		}
	}

	private HashSet<GameEvent> events = new HashSet<GameEvent>();

	private HashSet<Entity> entities = new HashSet<Entity>();

	private void processEventQueue() {
		//Determine what should happen with each event, and which events to ignore. Flagging entities as you go.
		//Once all flags have been distributed, actually execute the required operation on each
	}
	public void create() {
		//Initialise everything
	}
	public void render() {
		//Handle input
		checkInput();
		//Process events
		processEventQueue();

		//Draw furthest background layer
		//Draw middle background layer
		//Draw closest background layer
		//Draw foreground
		//Draw animals
		//Draw items
		//Draw player
		//Draw GUI
	}
}