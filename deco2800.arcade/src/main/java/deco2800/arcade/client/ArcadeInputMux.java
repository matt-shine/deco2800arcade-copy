package deco2800.arcade.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class ArcadeInputMux extends InputMultiplexer {

	public static InputMultiplexer inst = null;
	
	private ArcadeInputMux() {
		super();
	}
	
	//FIXME this seems like a broken version of static singleton?
	public static InputMultiplexer getInstance() {
		if (inst == null) {
			inst = new ArcadeInputMux();
			Gdx.input.setInputProcessor(inst);
		}
		return inst;
	}
	
}
