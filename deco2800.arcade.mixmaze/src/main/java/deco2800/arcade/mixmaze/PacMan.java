package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

class PacMan extends Actor {
	public final static String LOG = PacMan.class.getSimpleName();

	private Texture texture;
	private TextureRegion region;

	private class PacManInputListener extends InputListener {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			Actor actor = event.getListenerActor();

			System.out.println("actor=" + actor);

			Gdx.app.debug(LOG, keycode + " pressed");
			switch (keycode) {
				case LEFT:
					actor.addAction(moveBy(-128f, 0f));
					return true;
				case RIGHT:
					actor.addAction(moveBy(128f, 0f));
					return true;
				case UP:
					actor.addAction(moveBy(0f, 128f));
					return true;
				case DOWN:
					actor.addAction(moveBy(0f, -128f));
					return true;
			}

			return false;
		}
	}

	PacMan() {
		texture = new Texture(Gdx.files.internal("pacman.png"));
		region = new TextureRegion(texture, 0, 0, 512, 512);
		this.setX(100f);
		this.setY(100f);
		this.addListener(new PacManInputListener());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.draw(texture, 10, 10, 128, 128);
		batch.setColor(0f, 1f, 1f, 1f);
		batch.draw(region, this.getX(), this.getY(), 128, 128);
	}


}
