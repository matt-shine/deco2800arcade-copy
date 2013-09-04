package deco2800.arcade.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class ArcadeInputMux extends InputMultiplexer {

	public static InputMultiplexer inst = null;
	
	public static InputMultiplexer getInstance() {
		if (inst == null) {
			inst = new InputMultiplexer();
			Gdx.input.setInputProcessor(inst);
		}
		return inst;
	}
	
	
	
	
}
