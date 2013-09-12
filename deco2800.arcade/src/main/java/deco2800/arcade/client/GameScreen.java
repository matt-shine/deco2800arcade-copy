package deco2800.arcade.client;

import com.badlogic.gdx.Screen;

public abstract class GameScreen implements Screen {

	private int width = 0;
	private int height = 0;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public void resize(int width, int height) {
		boolean firstCall = false;
		if (this.width == 0 && this.height == 0) {
			firstCall = true;
		}
		this.width = width;
		this.height = height;

		if (firstCall) {
			firstResize();
		}

	}
	
	public void firstResize() {
		//TODO method contents? abstract?
	}

}
