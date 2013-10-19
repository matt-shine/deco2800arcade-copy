package deco2800.arcade.snakeLadder;

import org.apache.log4j.BasicConfigurator;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public class TestingStarter extends GameClient{
	public TestingStarter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		LwjglApplicationConfiguration cfg =
				new LwjglApplicationConfiguration();

		cfg.title = "Testing SnakeLadder";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;

		new LwjglApplication(new SnakeLadder(null, null,"Test Player"), cfg);
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
}
