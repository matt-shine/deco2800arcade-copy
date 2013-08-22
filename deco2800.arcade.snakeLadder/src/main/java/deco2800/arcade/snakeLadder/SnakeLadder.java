package deco2800.arcade.snakeLadder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

/**
 * This is the main class for game snake&Ladder
 * @author s4310055
 *
 */

@ArcadeGame(id="snakeLadder")
public class SnakeLadder extends GameClient {

	public SnakeLadder(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

}
